public class SongList extends Play{
    private Play play;

    public SongList(Play play){
        super();
    }
    
    public void generateNotesNeed(int frameWidth) {
        if(play != null){}
        play.generateNoteIfNeeded(300, frameWidth / 48 * 5);
        play.generateNoteIfNeeded(825, frameWidth / 48 * 5);
        play.generateNoteIfNeeded(1350, frameWidth / 48 * 29);
        play.generateNoteIfNeeded(1875, frameWidth / 48 * 29);
        play.generateNoteIfNeeded(2400, frameWidth / 48 * 41);
        play.generateNoteIfNeeded(2925, frameWidth / 48 * 41);
        play.generateNoteIfNeeded(3450, frameWidth / 48 * 29);
        play.generateNoteIfNeeded(3975, frameWidth / 48 * 29);
        play.generateNoteIfNeeded(4500, frameWidth / 48 * 17);
        play.generateNoteIfNeeded(5025, frameWidth / 48 * 17);
        play.generateNoteIfNeeded(5550, frameWidth / 48 * 5);
        play.generateNoteIfNeeded(6075, frameWidth / 48 * 5);
        play.generateNoteIfNeeded(6600, frameWidth / 48 * 41);
        play.generateNoteIfNeeded(7125, frameWidth / 48 * 41);
        play.generateNoteIfNeeded(7650, frameWidth / 48 * 29);
        play.generateNoteIfNeeded(7912, frameWidth / 48 * 5);// first intro
        play.generateNoteIfNeeded(9225, frameWidth / 48 * 5);
        play.generateNoteIfNeeded(9750, frameWidth / 48 * 5);
        play.generateNoteIfNeeded(10275, frameWidth / 48 * 29);
        play.generateNoteIfNeeded(10800, frameWidth / 48 * 29);
        play.generateNoteIfNeeded(11325, frameWidth / 48 * 41);
        play.generateNoteIfNeeded(11850, frameWidth / 48 * 41);
        play.generateNoteIfNeeded(12375, frameWidth / 48 * 29);
        play.generateNoteIfNeeded(12900, frameWidth / 48 * 29);
        play.generateNoteIfNeeded(13425, frameWidth / 48 * 17);
        play.generateNoteIfNeeded(13950, frameWidth / 48 * 17);
        play.generateNoteIfNeeded(14475, frameWidth / 48 * 5);
        play.generateNoteIfNeeded(15000, frameWidth / 48 * 5);
        play.generateNoteIfNeeded(15525, frameWidth / 48 * 41);
        play.generateNoteIfNeeded(16050, frameWidth / 48 * 41);
        play.generateNoteIfNeeded(16575, frameWidth / 48 * 29);
        play.generateNoteIfNeeded(16837, frameWidth / 48 * 5);
    }
}
