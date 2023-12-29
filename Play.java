import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
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
import javax.swing.SwingWorker;
import javax.swing.Timer;

public class Play extends GamePanel implements KeyListener, LineListener {

    // Screen Size
    private int songNumber;

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
    private int NOTE_SPEED = 5;
    protected Queue<Note> notes = new LinkedList<>();
    protected int noteIndex;
    private int hitAreaHeight;
    private boolean notesGeneratedFor = false;
    private int hitAreaWidth[] = new int[4];

    // Game audio input
    private AudioInputStream mozart;
    private AudioInputStream spiritedAway;
    protected Clip music;
    private Timer audioTimer;

    // music note timing
    private int[][] mozartTiming;
    private int timeIndex;
    private int noteNumber;
    private int[][] spiritedAwayTiming;
    // other
    private DecimalFormat df = new DecimalFormat();
    private Image MozartResizedImage;
    private Image SpiritedAwayResizedImage;
    private int iconWidthPropotion;
    private int combo;
    private int maxCombo;

    public Play(int score, int perfect, int good, int bad,
            int miss, int timeIndex, int noteNumber, int combo) {
        this.score = score;
        this.perfect = perfect;
        this.good = good;
        this.bad = bad;
        this.miss = miss;
        this.timeIndex = timeIndex;
        this.noteNumber = noteNumber;
        this.combo = combo;
    }

    public Play(int songNumber) {
        super();
        this.songNumber = songNumber;

        // set hit area location
        hitAreaLocation();

        // set music note timing
        setTiming();
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

    protected void initializeComponents() {

    }

    // KeyEvent method
    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        for (Iterator<Note> iterator = notes.iterator(); iterator.hasNext();) {
            Note note = iterator.next();
            if (note.y >= hitAreaHeight - 30 && note.y <= hitAreaHeight + 30) {
                if ((keyCode == KeyEvent.VK_D && note.x == hitAreaWidth[0]) ||
                        (keyCode == KeyEvent.VK_F && note.x == hitAreaWidth[1]) ||
                        (keyCode == KeyEvent.VK_J && note.x == hitAreaWidth[2]) ||
                        (keyCode == KeyEvent.VK_K && note.x == hitAreaWidth[3])) {
                    iterator.remove();
                    score++;
                    perfect++;
                    combo++;
                }
            } else if (note.y <= hitAreaHeight - 31 && note.y >= hitAreaHeight - 50) {
                if ((keyCode == KeyEvent.VK_D && note.x == hitAreaWidth[0]) ||
                        (keyCode == KeyEvent.VK_F && note.x == hitAreaWidth[1]) ||
                        (keyCode == KeyEvent.VK_J && note.x == hitAreaWidth[2]) ||
                        (keyCode == KeyEvent.VK_K && note.x == hitAreaWidth[3])) {
                    iterator.remove();
                    score++;
                    good++;
                    combo++;
                }
            } else if (note.y >= hitAreaHeight + 31 && note.y <= hitAreaHeight + 50) {
                if ((keyCode == KeyEvent.VK_D && note.x == hitAreaWidth[0]) ||
                        (keyCode == KeyEvent.VK_F && note.x == hitAreaWidth[1]) ||
                        (keyCode == KeyEvent.VK_J && note.x == hitAreaWidth[2]) ||
                        (keyCode == KeyEvent.VK_K && note.x == hitAreaWidth[3])) {
                    iterator.remove();
                    score++;
                    bad++;
                    combo++;
                }
            }
        }
        if (keyCode == KeyEvent.VK_ESCAPE) {
            stop();
            Result result = new Result(score, perfect, good, bad, miss, songNumber, maxCombo);
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
        switch (songNumber) {
            case 0:
                g.drawImage(MozartResizedImage, frameWidth / 2 - iconWidthPropotion / 2, 0, this);
                break;

            case 1:
                g.drawImage(SpiritedAwayResizedImage, frameWidth / 2 - iconWidthPropotion / 2, 0, this);
                break;
        }

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
        g.setFont(new Font("Algerian", Font.PLAIN, 24));
        g.drawString("Timer :" + df.format(time) + "s", frameWidth * 8 / 10, frameHeight * 4 / 100);

        // draw note
        for (Note note : notes) {
            g.setColor(note.color);
            g.fillOval(note.x, note.y, NOTE_SIZE, NOTE_SIZE);
        }

        // draw combo
        g.setFont(new Font("Algerian", Font.PLAIN, 50));
        g.setColor(Color.WHITE);
        if (combo >= 2 && combo <= 9) {
            g.drawString("Combo", frameWidth * 45 / 100, frameHeight * 1 / 10);
            g.drawString("" + combo, frameWidth * 49 / 100, frameHeight * 2 / 10);
        } else if (combo >= 10) {
            g.drawString("Combo", frameWidth * 45 / 100, frameHeight * 1 / 10);
            g.drawString("" + combo, frameWidth * 48 / 100, frameHeight * 2 / 10);
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
        switch (songNumber) {
            case 0:
                try {
                    mozart = AudioSystem.getAudioInputStream(new File(".au/mozart.au"));
                    music = AudioSystem.getClip();
                    music.open(mozart);
                    music.addLineListener(this);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;

            case 1:
                try {
                    spiritedAway = AudioSystem.getAudioInputStream(new File(".au/AlwaysWithMe.au"));
                    music = AudioSystem.getClip();
                    music.open(spiritedAway);
                    music.addLineListener(this);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
        }

    }

    // set time
    public void setTime() {
        startTime = System.currentTimeMillis();
        timer = new Timer(1, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                currentTime = System.currentTimeMillis();
                time = currentTime - startTime;
                generateNotes();
                reset();
                getScore();
                moveNote();
                getMaxCombo();
                repaint();
                revalidate();
            }
        });
        timer.start();
    }

    // control audio
    public void audioControl() {
        audioTimer = new Timer(900, new ActionListener() {
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
                miss++;
                combo = 0;
            }
        }

    }

    // set note generate time and position
    public void generateNotes() {
        switch (songNumber) {
            case 0:
                generateNoteIfNeeded(mozartTiming[0][noteNumber], mozartTiming[1][noteNumber]);

                if (time >= 20000) {
                    stop();
                    Result result = new Result(score, perfect, good, bad, miss, songNumber, maxCombo);
                    MenuPage.frame.setContentPane(result);
                    // Refresh the frame to show the new content
                    MenuPage.frame.revalidate();
                    MenuPage.frame.repaint();
                }
                break;

            case 1:
                generateNoteIfNeeded(spiritedAwayTiming[0][noteNumber], spiritedAwayTiming[1][noteNumber]);
                if (time >= 32100) {
                    stop();
                    Result result = new Result(score, perfect, good, bad, miss, songNumber, maxCombo);
                    MenuPage.frame.setContentPane(result);
                    // Refresh the frame to show the new content
                    MenuPage.frame.revalidate();
                    MenuPage.frame.repaint();
                }
                break;
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
            runNoteIndex();
        }
    }

    private void runNoteIndex() {
        if (notesGeneratedFor) {
            switch (songNumber) {
                case 0:
                    if (timeIndex != mozartTiming[0].length - 1 && noteNumber != mozartTiming[1].length - 1) {
                        timeIndex++;
                        noteNumber++;
                    }
                    break;

                case 1:
                    if (timeIndex != spiritedAwayTiming[0].length - 1
                            && noteNumber != spiritedAwayTiming[1].length - 1) {
                        timeIndex++;
                        noteNumber++;
                    }
                    break;
            }
            reset();
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
        MozartResizedImage = orgImage.getScaledInstance(iconWidthPropotion, iconHeightPropotion,
                Image.SCALE_SMOOTH);

        icon = new ImageIcon(".img/PlaySpiritedAway.jpg");
        iconHeight = icon.getIconHeight();
        iconWidth = icon.getIconWidth();
        orgImage = icon.getImage();
        SpiritedAwayResizedImage = orgImage.getScaledInstance(iconWidthPropotion, iconHeightPropotion,
                Image.SCALE_SMOOTH);
    }

    private void hitAreaLocation() {
        hitAreaHeight = frameHeight * 8 / 10;
        hitAreaWidth[0] = frameWidth * 5 / 48;
        hitAreaWidth[1] = frameWidth * 17 / 48;
        hitAreaWidth[2] = frameWidth * 29 / 48;
        hitAreaWidth[3] = frameWidth * 41 / 48;
    }

    private void setTiming() {
        this.mozartTiming = new int[][] {
                { 400, 925, 1450, 1975, 2500, 3025, 3550, 4075, 4600, 5125, 5650, 6175, 6700, 7225, 7750, 8015, 9325,
                        9850, 10375, 10900, 11425, 11950, 12475, 13000, 13525, 14050, 14575, 15100, 15625, 16150, 16675,
                        16937 },
                { hitAreaWidth[0], hitAreaWidth[0], hitAreaWidth[2], hitAreaWidth[2], hitAreaWidth[3], hitAreaWidth[3],
                        hitAreaWidth[2], hitAreaWidth[2], hitAreaWidth[1], hitAreaWidth[1], hitAreaWidth[0],
                        hitAreaWidth[0], hitAreaWidth[3], hitAreaWidth[3], hitAreaWidth[2], hitAreaWidth[0],
                        hitAreaWidth[0], hitAreaWidth[0], hitAreaWidth[2], hitAreaWidth[2], hitAreaWidth[3],
                        hitAreaWidth[3], hitAreaWidth[2], hitAreaWidth[2], hitAreaWidth[1], hitAreaWidth[1],
                        hitAreaWidth[0], hitAreaWidth[0], hitAreaWidth[3], hitAreaWidth[3], hitAreaWidth[2],
                        hitAreaWidth[0] }
        };

        this.spiritedAwayTiming = new int[][] {
                { 4850, 5110, 5370, 5630, 5890, 6670, 6930, 7450, 7970, 8490, 8750, 9010, 9790, 10050, 11090, 11610,
                        12130, 12650, 12910, 13170, 13690, 14210, 14470, 14690, 15210, 15470, 15730, 15990, 16350,
                        17440, 17700, 17960, 18220, 18480, 19260, 19520, 20040, 20560, 21080, 21340, 21600, 22120,
                        22380, 22640, 23940, 24200, 24720, 25240, 25500, 25760, 26280, 26800, 27060, 27320, 27840,
                        28100, 28360, 28620, 28880 },
                { hitAreaWidth[0], hitAreaWidth[1], hitAreaWidth[2], hitAreaWidth[0], hitAreaWidth[3], hitAreaWidth[2],
                        hitAreaWidth[1], hitAreaWidth[3], hitAreaWidth[1], hitAreaWidth[2], hitAreaWidth[1],
                        hitAreaWidth[3], hitAreaWidth[1], hitAreaWidth[0], hitAreaWidth[2], hitAreaWidth[1],
                        hitAreaWidth[0], hitAreaWidth[1], hitAreaWidth[2], hitAreaWidth[1], hitAreaWidth[3],
                        hitAreaWidth[0], hitAreaWidth[2], hitAreaWidth[3], hitAreaWidth[1], hitAreaWidth[2],
                        hitAreaWidth[0], hitAreaWidth[1], hitAreaWidth[3], hitAreaWidth[1], hitAreaWidth[2],
                        hitAreaWidth[0], hitAreaWidth[1], hitAreaWidth[2], hitAreaWidth[1], hitAreaWidth[3],
                        hitAreaWidth[0], hitAreaWidth[2], hitAreaWidth[3], hitAreaWidth[1], hitAreaWidth[2],
                        hitAreaWidth[0], hitAreaWidth[1], hitAreaWidth[3], hitAreaWidth[1], hitAreaWidth[2],
                        hitAreaWidth[0], hitAreaWidth[2], hitAreaWidth[3], hitAreaWidth[1], hitAreaWidth[2],
                        hitAreaWidth[0], hitAreaWidth[1], hitAreaWidth[3], hitAreaWidth[1], hitAreaWidth[2],
                        hitAreaWidth[0], hitAreaWidth[1], hitAreaWidth[0] }
        };
    }

    private void getMaxCombo() {
        if (combo > maxCombo) {
            maxCombo = combo;
        }
    }
}
