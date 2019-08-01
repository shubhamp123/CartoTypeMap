package com.example.cartotypemap;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.widget.FrameLayout;

import com.cartotype.Framework;
import com.cartotype.MapView;
import com.cartotype.NoticePosition;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class MainActivity extends AppCompatActivity {

    private FrameLayout frameLayout = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        frameLayout = findViewById(R.id.frame);

        try {
            File mapFile = getFileFromRaw(this, R.raw.berlin, "berlinmap");
            File fontFile = getFileFromRaw(this, R.raw.dejavusans, "dejavusansfont");
            File styleFile = getFileFromRaw(this, R.raw.standard, "standardstyle");

            Log.d("WASTE", "mapFile: "+mapFile.getAbsolutePath());
            Log.d("WASTE", "fontFile: "+fontFile.getAbsolutePath());
            Log.d("WASTE", "styleFile: "+styleFile.getAbsolutePath());

            Framework framework = new Framework(this,
                    mapFile.getAbsolutePath(),
                    styleFile.getAbsolutePath(),
                    fontFile.getAbsolutePath(),
                    2500,
                    5000);


            framework.setFollowMode(Framework.FOLLOW_FLAG_HEADING);

            frameLayout.addView(new MapView(this, framework));

            /*

            add layers
            * */


        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private File getFileFromRaw(Context context, int resourceId, String outName) throws IOException {

            File fileOut = new File(context.getFilesDir()
                            .getAbsolutePath()+ File.separator+outName);

            if(!fileOut.exists()){

                InputStream in = context.getResources().openRawResource(resourceId);
                OutputStream outStream = new FileOutputStream(fileOut);

                byte[] buff = new byte[1024];
                int length = -1;

                while((length = in.read(buff)) > 0){

                    outStream.write(buff,0,length);
                }

                outStream.flush();
                outStream.close();
                in.close();

            }

            return fileOut;

    }
}
