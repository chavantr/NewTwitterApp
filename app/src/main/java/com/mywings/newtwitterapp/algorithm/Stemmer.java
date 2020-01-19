package com.mywings.newtwitterapp.algorithm;

/**
 * Stemmer, implementing the Porter Stemming Algorithm
 * <p>
 * The Stemmer class transforms a word into its root form. The input word can be
 * provided a character at time (by calling add()), or at once by calling one of
 * the various stem(something) methods.
 */

public class Stemmer {
    private char[] b;
    private int i, /* offset into b */
            i_end, /* offset to end of stemmed word */
            j, k;
    private static final int INC = 50;

    /* unit of size whereby b is increased */
    public Stemmer() {
        b = new char[INC];
        i = 0;
        i_end = 0;
    }

    /**
     * Add a character to the word being stemmed. When you are finished adding
     * characters, you can call stem(void) to stem the word.
     *
     * @param ch
     */

    public void add(char ch) {
        if (i == b.length) {
            char[] new_b = new char[i + INC];
            System.arraycopy(b, 0, new_b, 0, i);
            b = new_b;
        }
        b[i++] = ch;
    }

    /**
     * Adds wLen characters to the word being stemmed contained in a portion of
     * a char[] array. This is like repeated calls of add(char ch), but faster.
     *
     * @param w
     * @param wLen
     */

    public void add(char[] w, int wLen) {
        if (i + wLen >= b.length) {
            char[] new_b = new char[i + wLen + INC];
            System.arraycopy(b, 0, new_b, 0, i);
            b = new_b;
        }
        for (int c = 0; c < wLen; c++)
            b[i++] = w[c];
    }

    /**
     * After a word has been stemmed, it can be retrieved by toString(), or a
     * reference to the internal buffer can be retrieved by getResultBuffer and
     * getResultLength (which is generally more efficient.)
     *
     * @return
     */
    public String toString() {
        return new String(b, 0, i_end);
    }

    /**
     * Returns the length of the word resulting from the stemming process.
     *
     * @return
     */
    public int getResultLength() {
        return i_end;
    }

    /**
     * Returns a reference to a character buffer containing the results of the
     * stemming process. You also need to consult getResultLength() to determine
     * the length of the result.
     *
     * @return
     */
    public char[] getResultBuffer() {
        return b;
    }

    /* cons(i) is true <=> b[i] is a consonant. */

    private boolean cons(int i) {
        switch (b[i]) {
            case 'a':
            case 'e':
            case 'i':
            case 'o':
            case 'u':
                return false;
            case 'y':
                return (i == 0) ? true : !cons(i - 1);
            default:
                return true;
        }
    }

    /*
     * m() measures the number of consonant sequences between 0 and j. if c is a
     * consonant sequence and v a vowel sequence, and <..> indicates arbitrary
     * presence,
     *
     * <c><v> gives 0 <c>vc<v> gives 1 <c>vcvc<v> gives 2 <c>vcvcvc<v> gives 3
     * ....
     */

    private int m() {
        int n = 0;
        int i1 = 0;
        while (true) {
            if (i1 > j)
                return n;
            if (!cons(i1))
                break;
            i1++;
        }
        i1++;
        while (true) {
            while (true) {
                if (i1 > j)
                    return n;
                if (cons(i1))
                    break;
                i1++;
            }
            i1++;
            n++;
            while (true) {
                if (i1 > j)
                    return n;
                if (!cons(i1))
                    break;
                i1++;
            }
            i1++;
        }
    }

    /* vowelinstem() is true <=> 0,...j contains a vowel */

    private boolean vowelinstem() {
        int i2;
        for (i2 = 0; i2 <= j; i2++)
            if (!cons(i2))
                return true;
        return false;
    }

    /* doublec(j) is true <=> j,(j-1) contain a double consonant. */

    private boolean doublec(int j) {
        if (j < 1)
            return false;
        if (b[j] != b[j - 1])
            return false;
        return cons(j);
    }

    /*
     * cvc(i) is true <=> i-2,i-1,i has the form consonant - vowel - consonant
     * and also if the second c is not w,x or y. this is used when trying to
     * restore an e at the end of a short word. e.g.
     *
     * cav(e), lov(e), hop(e), crim(e), but snow, box, tray.
     *
     */

    private boolean cvc(int i) {
        if (i < 2 || !cons(i) || cons(i - 1) || !cons(i - 2))
            return false;
        {
            int ch = b[i];
            if (ch == 'w' || ch == 'x' || ch == 'y')
                return false;
        }
        return true;
    }

    private boolean ends(String s) {
        int l = s.length();
        int o = k - l + 1;
        if (o < 0)
            return false;
        for (int i3 = 0; i3 < l; i3++)
            if (b[o + i3] != s.charAt(i3))
                return false;
        j = k - l;
        return true;
    }

    /*
     * setto(s) sets (j+1),...k to the characters in the string s, readjusting
     * k.
     */

    private void setto(String s) {
        int l = s.length();
        int o = j + 1;
        for (int i4 = 0; i4 < l; i4++)
            b[o + i4] = s.charAt(i4);
        k = j + l;
    }

    /* r(s) is used further down. */

    private void r(String s) {
        if (m() > 0)
            setto(s);
    }

    /*
     * step1() gets rid of plurals and -ed or -ing. e.g.
     *
     * caresses -> caress ponies -> poni ties -> ti caress -> caress cats -> cat
     *
     * feed -> feed agreed -> agree disabled -> disable
     *
     * matting -> mat mating -> mate meeting -> meet milling -> mill messing ->
     * mess
     *
     * meetings -> meet
     *
     */

    private void step1() {
        if (b[k] == 's') {
            if (ends("sses"))
                k -= 2;
            else if (ends("es"))
                k -= 1;
            else if (ends("ies"))
                setto("y");
            else if (b[k - 2] != 's')
                k--;

        }
        if (ends("ed")) {
            if (m() > 0)
                k--;
        } else if ((ends("ed") || ends("ing")) && vowelinstem()) {
            k = j;
            if (ends("at"))
                setto("at");
            else if (ends("bl"))
                setto("ble");
            else if (ends("iz"))
                setto("ize");
            else if (doublec(k)) {
                k--;
                {
                    int ch = b[k];
                    if (ch == 'l' || ch == 's' || ch == 'z')
                        k++;
                }
            } else if (m() == 1 && cvc(k))
                setto("e");
        }
    }

    /* step2() turns terminal y to i when there is another vowel in the stem. */

    private void step2() {
        if (ends("ity") && vowelinstem())
            b[k] = 'i';
        if (ends("ly") && vowelinstem())
            k -= 2;
    }

    /*
     * step3() maps double suffices to single ones. so -ization ( = -ize plus
     * -ation) maps to -ize etc. note that the string before the suffix must
     * give m() > 0.
     */

    private void step3() {
        if (k == 0)
            return;
        /* For Bug 1 */
        switch (b[k - 1]) {
            case 'a':
                if (ends("ational")) {
                    r("tion");
                    break;
                }
                if (ends("tional")) {
                    r("tion");
                    break;
                }

                break;
            case 'c':
                if (ends("ency")) {
                    r("ent");
                    break;
                }
                if (ends("anci")) {
                    r("ance");
                    break;
                }
                break;
            case 'e':
                if (ends("izer")) {
                    r("ize");
                    break;
                }
                break;
            case 'l':
                if (ends("bli")) {
                    r("ble");
                    break;
                }
                if (ends("alli")) {
                    r("al");
                    break;
                }
                if (ends("entli")) {
                    r("ent");
                    break;
                }
                if (ends("eli")) {
                    r("e");
                    break;
                }
                if (ends("ousli")) {
                    r("ous");
                    break;
                }
                break;
            case 'o':
                if (ends("ization")) {
                    r("ize");
                    break;
                }
                if (ends("ation")) {
                    r("ation");
                    break;
                }
                if (ends("ator")) {
                    r("ate");
                    break;
                }
                break;
            case 's':
                if (ends("alism")) {
                    r("al");
                    break;
                }
                if (ends("iveness")) {
                    r("ive");
                    break;
                }
                if (ends("fulness")) {
                    r("ful");
                    break;
                }
                if (ends("ousness")) {
                    r("ous");
                    break;
                }
                break;
            case 't':
                if (ends("aliti")) {
                    r("al");
                    break;
                }
                if (ends("iviti")) {
                    r("ive");
                    break;
                }
                if (ends("biliti")) {
                    r("ble");
                    break;
                }
                break;
            case 'g':
                if (ends("logi")) {
                    r("log");
                    break;
                }
        }
    }

    /* step4() deals with -ic-, -full, -ness etc. similar strategy to step3. */

    private void step4() {
        switch (b[k]) {
            case 'e':
                if (ends("icate")) {
                    r("icat");
                    break;
                }
                if (ends("ative")) {
                    r("");
                    break;
                }
                if (ends("alize")) {
                    r("al");
                    break;
                }
                break;
            case 'i':
                if (ends("iciti")) {
                    r("ic");
                    break;
                }
                break;
            case 'l':
                if (ends("ical")) {
                    r("ic");
                    break;
                }
                if (ends("ful")) {
                    r("");
                    break;
                }

                break;
            case 's':
                if (ends("ness")) {
                    r("");
                    break;
                }
                break;
        }
    }

    /* step5() takes off -ant, -ence etc., in context <c>vcvc<v>. */

    private void step5() {
        if (k == 0)
            return;
        /* for Bug 1 */
        switch (b[k - 1]) {
            case 'a':
                if (ends("al"))
                    break;
                return;
            case 'c':
                if (ends("ance"))
                    break;
                if (ends("ence"))
                    break;
                return;
            case 'e':
                if (ends("er"))
                    break;
                return;
            case 'i':
                if (ends("ic"))
                    break;
                return;
            case 'l':
                if (ends("able"))
                    break;
                if (ends("ible"))
                    break;
                return;
            case 'n':
                if (ends("ant"))
                    break;
                if (ends("ment"))
                    break;
                if (ends("ment"))
                    break;
                /* element etc. not stripped before the m */
                // if (ends("ent")) break; return;
            case 'o':
                if (ends("ion") && j >= 0 && (b[j] == 's'))
                    break;
                /* j >= 0 fixes Bug 2 */
                // if (ends("ou")) break; return;
                /* takes care of -ous */
            case 's':
                if (ends("ism"))
                    break;
                return;
            case 't':
                if (ends("ate"))
                    break;
                if (ends("iti"))
                    break;
                return;
            case 'u':
                if (ends("ous"))
                    break;
                return;

            case 'v':
                if (ends("ive"))
                    break;
                return;
            case 'z':
                if (ends("ize"))
                    break;
                return;
            default:
                return;
        }
        if (m() > 1)
            k = j;
    }

    /* step6() removes a final -e if m() > 1. */

    private void step6() {
        j = k;
    }

    public void stem() {
        k = i - 1;
        if (k > 1) {
            step1();
            step2();
            step3();
            step4();
            step5();
            step6();
        }
        i_end = k + 1;
        i = 0;
    }

    /**
     * Test program for demonstrating the Stemmer. It reads text from a a list
     * of files, stems each word, and writes the result to standard output. Note
     * that the word stemmed is expected to be in lower case: forcing lower case
     * must be done outside the Stemmer class. Usage: Stemmer file-name
     * file-name ...
     *
     * @param filepath
     * @return
     */
    public String getData(String filepath) {
        StringBuilder sb = new StringBuilder();

        char[] w = new char[501];
        //Stemmer s = new Stemmer();

        //FileInputStream in = new FileInputStream(p[0]);

            /*while (true)

            {*/
        //int ch = in.read();
        for (int l = 0; l < filepath.length(); l++) {


            if (Character.isLetter((char) filepath.charAt(l))) {
                int j1 = 0;
               // while (true) {
                    //ch = Character.toLowerCase((char) filepath.charAt(l));
                    w[j1] = Character.toLowerCase(filepath.charAt(l));
                    if (j1 < 500)
                        j1++;
                    //ch = filepath.charAt(l);
                    if (!Character.isLetter((char) filepath.charAt(l))) {
                        /* to test add(char ch) */
                        for (int c = 0; c < j1; c++)
                            add(w[c]);
                        stem();
                        {
                            String u;
                            u = toString();
                            sb.append(u);
                        }
                        break;
                    }
                //}
            }
            if (filepath.charAt(l) < 0)
                break;
            sb.append((char) filepath.charAt(l));
        }
        return sb.toString();
    }


}