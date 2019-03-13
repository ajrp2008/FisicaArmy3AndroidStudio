package processing.test.fisicaarmy3;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.GZIPInputStream;

import processing.core.PApplet;

public class PAppletFisicaArmy extends PApplet {

    @Override
    public InputStream createInput(String filename) {
        //InputStream input = this.createInputRaw(filename);
        InputStream input = null;
        try {
            input = FisicaArmy3.fiscaArmy3.getActivity().getAssets().open("new.csv");
        } catch (IOException e) {
            e.printStackTrace();
        }
        String lower = filename.toLowerCase();
        if (input != null && (lower.endsWith(".gz") || lower.endsWith(".svgz"))) {
            try {
                return new BufferedInputStream(new GZIPInputStream(input));
            } catch (IOException var5) {
                this.printStackTrace(var5);
                return null;
            }
        } else {
            return new BufferedInputStream(input);
        }
    }
}
