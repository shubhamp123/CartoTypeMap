package com.example.cartotypemap;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.FrameLayout;

import com.cartotype.Address;
import com.cartotype.CoordType;
import com.cartotype.Framework;
import com.cartotype.MapView;
import com.cartotype.NoticePosition;
import com.cartotype.Route;
import com.cartotype.RouteCoordSet;
import com.cartotype.RoutePoint;
import com.cartotype.RouteProfile;
import com.cartotype.RouteProfileType;

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

            final Framework framework = new Framework(this,
                    mapFile.getAbsolutePath(),
                    styleFile.getAbsolutePath(),
                    fontFile.getAbsolutePath(),
                    2500,
                    5000);

            framework.setFollowMode(Framework.FOLLOW_FLAG_HEADING);

           /* framework.insertPushPin(13.378344,52.513923, CoordType.Degree, "","", 0, 0);
            framework.insertPushPin(13.377775,52.516266, CoordType.Degree, "","", 0, 0);
            framework.insertPushPin(13.376198,52.518623, CoordType.Degree, "","", 0, 0);
            framework.insertPushPin(13.402318,52.516640, CoordType.Degree, "","", 0, 0);
            framework.insertPushPin(13.401797,52.518898, CoordType.Degree, "","", 0, 0);
*/

            /*double lat[] ={52.513923, 52.516266, 52.518623, 52.516640, 52.518898};
            double lon[] ={13.378344, 13.377775, 13.376198, 13.402318, 13.401797};
            */
            double lat[] ={52.513923, 52.536164};
            double lon[] ={13.378344, 13.433664};

            framework.enableNavigation(true);
       
            framework.startNavigation(lon,lat,CoordType.Degree);




            final Handler handler = new Handler();
            Runnable runnableCode = new Runnable() {
                 double lonStart = 13.378344;
                 double latStart = 52.513923;
                @Override
                public void run() {

                    framework.navigate(Framework.POSITION_VALID,1.0,lonStart,latStart,20.0,0,0);
                    framework.setFollowMode(Framework.FOLLOW_MODE_LOCATION);
                    Log.d("waste","navigate "+lonStart+" "+latStart);

                    handler.postDelayed(this, 1000);
                }
            };
            handler.post(runnableCode);

            framework.setFollowMode(Framework.FOLLOW_MODE_LOCATION);


            frameLayout.addView(new MapView(this, framework));




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
