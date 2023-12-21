public class print {
    public static void main(String[] args) {
        int i = 7650;
        for (int f = 0; f < 20; f++) {
            System.out.println("generateNoteIfNeeded(" + i + ");");
            i += 525;
        }
    }
}
