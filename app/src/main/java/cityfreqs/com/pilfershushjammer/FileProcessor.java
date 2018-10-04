package cityfreqs.com.pilfershushjammer;


import android.content.Context;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class FileProcessor {
    protected Context context;
    private String[] audioSdkArray;

    protected FileProcessor(Context context) {
        this.context = context;
    }

    protected String[] getAudioSdkArray() {
        // should always be an internal list of size > 1
        //TODO fix the logic here
        if (audioSdkArray == null) {
            // maybe not created yet...
            if (loadAudioSdkList()) {
                return audioSdkArray;
            }
            else {
                // error in finding and loading internal sdk list
                return null;
            }
        }
        else if (audioSdkArray.length > 0)
            return audioSdkArray;
        else {
            // no list made, trigger it
            if (loadAudioSdkList()) {
                return audioSdkArray;
            }
            else {
                // error in finding and loading internal sdk list
                return null;
            }
        }
    }

    private boolean loadAudioSdkList() {
        // BackScan internal list of audio beacon sdk package names
        try {
            InputStream audioSdkInput = context.getResources().openRawResource(R.raw.audio_sdk_names);
            BufferedReader audioSdkStream = new BufferedReader(new InputStreamReader(audioSdkInput));

            ArrayList<String> audioSdkList = new ArrayList<>();
            String audioSdkLine;
            while ((audioSdkLine = audioSdkStream.readLine()) != null) {
                audioSdkList.add(audioSdkLine);
            }
            // clean up
            audioSdkInput.close();
            audioSdkStream.close();
            // convert list to array
            if (audioSdkList.isEmpty()) {
                return false;
            }
            else {
                audioSdkArray = audioSdkList.toArray(new String[audioSdkList.size()]);
                return true;
            }
        }
        catch (Exception ex) {
            // error
            return false;
        }
    }
}
