import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.text.DecimalFormat;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineListener;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.SwingWorker;
import javax.swing.Timer;

public class Play extends JPanel implements KeyListener, LineListener {

    // Screen Size
    private int frameHeight;
    private int frameWidth;
    private Dimension screenSize;

    // Game State
    private int score;
    private int perfect;
    private int good;
    private int bad;
    private int miss;
    private long startTime;
    private long currentTime;
    protected long time;
    private Timer timer;

    // Game Object
    protected int NOTE_SIZE = 60;
    private int NOTE_SPEED = 3;
    protected Queue<Note> notes = new LinkedList<>();
    protected int noteIndex;
    private int hitAreaHeight;
    private boolean notesGeneratedFor = false;

    // Game audio input
    private AudioInputStream mozart;
    protected Clip music;
    private Timer audioTimer;

    // other
    private DecimalFormat df = new DecimalFormat();
    private Image resizedImage;
    private int iconWidthPropotion;

    public Play(Dimension screenSize, int frameHeight, int frameWidth, int score, int perfect, int good, int bad,
            int miss) {
        this.screenSize = screenSize;
        this.frameHeight = frameHeight;
        this.frameWidth = frameWidth;
        this.score = score;
        this.perfect = perfect;
        this.good = good;
        this.bad = bad;
        this.miss = miss;
    }

    public Play() {
        screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        frameHeight = screenSize.height;
        frameWidth = screenSize.width;
        hitAreaHeight = frameHeight * 8 / 10;

        // set image
        backgroundImage();
        // load audio
        loadAudio();

        // control audio play time
        audioControl();

        // Set and start time to run
        setTime();

        // set KeyListner
        addKeyListener(this);

    }

    // KeyEvent method
    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        for (Iterator<Note> iterator = notes.iterator(); iterator.hasNext();) {
            Note note = iterator.next();
            if (note.y >= hitAreaHeight - 20 && note.y <= hitAreaHeight + 20) {
                if ((keyCode == KeyEvent.VK_D && note.x == frameWidth / 48 * 5) ||
                        (keyCode == KeyEvent.VK_F && note.x == frameWidth / 48 * 17) ||
                        (keyCode == KeyEvent.VK_J && note.x == frameWidth / 48 * 29) ||
                        (keyCode == KeyEvent.VK_K && note.x == frameWidth / 48 * 41)) {
                    iterator.remove();
                    score++;
                    perfect++;
                }
            } else if (note.y <= hitAreaHeight - 21 && note.y >= hitAreaHeight - 60) {
                if ((keyCode == KeyEvent.VK_D && note.x == frameWidth / 48 * 5) ||
                        (keyCode == KeyEvent.VK_F && note.x == frameWidth / 48 * 17) ||
                        (keyCode == KeyEvent.VK_J && note.x == frameWidth / 48 * 29) ||
                        (keyCode == KeyEvent.VK_K && note.x == frameWidth / 48 * 41)) {
                    iterator.remove();
                    score++;
                    good++;
                }
            } else if (note.y >= hitAreaHeight + 21 && note.y <= hitAreaHeight + 60) {
                if ((keyCode == KeyEvent.VK_D && note.x == frameWidth / 48 * 5) ||
                        (keyCode == KeyEvent.VK_F && note.x == frameWidth / 48 * 17) ||
                        (keyCode == KeyEvent.VK_J && note.x == frameWidth / 48 * 29) ||
                        (keyCode == KeyEvent.VK_K && note.x == frameWidth / 48 * 41)) {
                    iterator.remove();
                    score++;
                    bad++;
                }
            }
        }
        if(keyCode == KeyEvent.VK_ESCAPE){
            stop();
            Result result = new Result(score, perfect, good, bad, miss);
            MenuPage.frame.setContentPane(result);
            // Refresh the frame to show the new content
            MenuPage.frame.revalidate();
            MenuPage.frame.repaint();
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    // LineListener methods
    @Override
    public void update(LineEvent e) {

    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        // clear background
        g.setColor(Color.black);
        g.fillRect(0, 0, frameWidth, frameHeight);

        // draw background
        drawBackground((Graphics2D) g);
        g.drawImage(resizedImage, frameWidth / 2 - iconWidthPropotion / 2, 0, this);

        // reset transparent
        resetTransparent((Graphics2D) g);

        // draw hit area
        int[] hitArea = { frameWidth * 5 / 48, frameWidth * 17 / 48, frameWidth * 29 / 48,
                frameWidth * 41 / 48 };
        g.setColor(Color.RED);
        for (int x : hitArea) {
            g.fillOval(x, hitAreaHeight, NOTE_SIZE, NOTE_SIZE);
        }

        // display Score
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.PLAIN, 24));
        g.drawString("Score :" + score, frameWidth * 1 / 100, frameHeight * 4 / 100);

        // draw dividing line
        g.setColor(Color.WHITE);
        g.drawLine(frameWidth / 4, 0, frameWidth / 4, frameHeight);
        g.drawLine(frameWidth / 2, 0, frameWidth / 2, frameHeight);
        g.drawLine(frameWidth / 4 * 3, 0, frameWidth / 4 * 3, frameHeight);

        // display time
        g.setFont(new Font("Arial", Font.PLAIN, 24));
        g.drawString("Timer :" + df.format(time) + "s", frameWidth * 8 / 10, frameHeight * 4 / 100);

        // draw note
        for (Note note : notes) {
            g.setColor(note.color);
            g.fillOval(note.x, note.y, NOTE_SIZE, NOTE_SIZE);
        }
    }

    private void drawBackground(Graphics2D g2d) {
        float alpha = 0.5f;
        AlphaComposite alphaComposite = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha);
        g2d.setComposite(alphaComposite);
    }

    private void resetTransparent(Graphics2D g2d) {
        float alphaOriginal = 1.0f;
        AlphaComposite alphaCompositeOriginal = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alphaOriginal);
        g2d.setComposite(alphaCompositeOriginal);
    }

    // play Audio
    public void playMusic() {
        if (music != null) {
            music.setFramePosition(0);
            music.start();
        }
    }

    // load Audio
    public void loadAudio() {
        try {
            mozart = AudioSystem.getAudioInputStream(new File("m1.au"));
            music = AudioSystem.getClip();
            music.open(mozart);
            music.addLineListener(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // set time
    public void setTime() {
        startTime = System.currentTimeMillis();
        timer = new Timer(10, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                currentTime = System.currentTimeMillis();
                time = currentTime - startTime;
                generateNotes();
                reset();
                getScore();
                moveNote();
                repaint();
                revalidate();
            }
        });
        timer.start();
    }

    // control audio
    public void audioControl() {
        audioTimer = new Timer(2300, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                playMusic();
            }
        });
        audioTimer.setRepeats(false);
        audioTimer.start();
    }

    // set Note
    public class Note {
        int x;
        int y;
        Color color = Color.BLUE;

        public Note(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    public void moveNote() {
        for (Iterator<Note> iterator = notes.iterator(); iterator.hasNext();) {
            Note note = iterator.next();
            note.y += NOTE_SPEED;
            if (note.y + NOTE_SIZE > frameHeight) {
                iterator.remove();
                if(score>0){
                    score--;
                }
                miss++;
            }
        }
    }

    // set note generate time and position
    public void generateNotes() {
        generateNoteIfNeeded(300, frameWidth / 48 * 5);
        generateNoteIfNeeded(825, frameWidth / 48 * 5);
        generateNoteIfNeeded(1350, frameWidth / 48 * 29);
        generateNoteIfNeeded(1875, frameWidth / 48 * 29);
        generateNoteIfNeeded(2400, frameWidth / 48 * 41);
        generateNoteIfNeeded(2925, frameWidth / 48 * 41);
        generateNoteIfNeeded(3450, frameWidth / 48 * 29);
        generateNoteIfNeeded(3975, frameWidth / 48 * 29);
        generateNoteIfNeeded(4500, frameWidth / 48 * 17);
        generateNoteIfNeeded(5025, frameWidth / 48 * 17);
        generateNoteIfNeeded(5550, frameWidth / 48 * 5);
        generateNoteIfNeeded(6075, frameWidth / 48 * 5);
        generateNoteIfNeeded(6600, frameWidth / 48 * 41);
        generateNoteIfNeeded(7125, frameWidth / 48 * 41);
        generateNoteIfNeeded(7650, frameWidth / 48 * 29);
        generateNoteIfNeeded(7912, frameWidth / 48 * 5);// first intro
        generateNoteIfNeeded(9225, frameWidth / 48 * 5);
        generateNoteIfNeeded(9750, frameWidth / 48 * 5);
        generateNoteIfNeeded(10275, frameWidth / 48 * 29);
        generateNoteIfNeeded(10800, frameWidth / 48 * 29);
        generateNoteIfNeeded(11325, frameWidth / 48 * 41);
        generateNoteIfNeeded(11850, frameWidth / 48 * 41);
        generateNoteIfNeeded(12375, frameWidth / 48 * 29);
        generateNoteIfNeeded(12900, frameWidth / 48 * 29);
        generateNoteIfNeeded(13425, frameWidth / 48 * 17);
        generateNoteIfNeeded(13950, frameWidth / 48 * 17);
        generateNoteIfNeeded(14475, frameWidth / 48 * 5);
        generateNoteIfNeeded(15000, frameWidth / 48 * 5);
        generateNoteIfNeeded(15525, frameWidth / 48 * 41);
        generateNoteIfNeeded(16050, frameWidth / 48 * 41);
        generateNoteIfNeeded(16575, frameWidth / 48 * 29);
        generateNoteIfNeeded(16837, frameWidth / 48 * 5);

        if (time >= 10000) {
            stop();
            Result result = new Result(score, perfect, good, bad, miss);
            MenuPage.frame.setContentPane(result);
            // Refresh the frame to show the new content
            MenuPage.frame.revalidate();
            MenuPage.frame.repaint();
        }
    }

    public void generateNoteIfNeeded(long targetTime, int position) {
        if (time >= targetTime - 10 && time <= targetTime + 10 && !notesGeneratedFor) {
            int x = position;
            int y = NOTE_SIZE;
            Note note = new Note(x, y);
            notes.add(note);
            noteIndex = (noteIndex + 1);
            notesGeneratedFor = true;
        }
    }

    // generate the note on the time
    public void reset() {
        // Introduce a delay after generating notes using SwingWorker
        int delayMillis = 1; // Adjust the delay duration as needed
        if (notesGeneratedFor == true) {
            new SwingWorker<Void, Void>() {
                @Override
                protected Void doInBackground() throws Exception {
                    Thread.sleep(delayMillis);
                    return null;
                }

                @Override
                protected void done() {
                    // Reset the flag after the delay
                    notesGeneratedFor = false;
                }
            }.execute();
        }
    }

    public void stop() {
        timer.stop();
        audioTimer.stop();
        music.stop();
        music.close();
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    private void backgroundImage() {
        ImageIcon icon = new ImageIcon(".img/Mozart.jpeg");
        int iconHeight = icon.getIconHeight();
        int iconWidth = icon.getIconWidth();

        int iconHeightPropotion = frameHeight;
        iconWidthPropotion = (int) Math.round((double) iconHeightPropotion / iconHeight * iconWidth);

        Image orgImage = icon.getImage();
        resizedImage = orgImage.getScaledInstance(iconWidthPropotion, iconHeightPropotion,
                Image.SCALE_SMOOTH);
    }
}
