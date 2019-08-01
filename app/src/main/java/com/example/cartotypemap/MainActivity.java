package com.example.cartotypemap;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.widget.FrameLayout;

import com.cartotype.CoordType;
import com.cartotype.Framework;
import com.cartotype.Geometry;
import com.cartotype.MapView;
import com.cartotype.NoticePosition;
import com.cartotype.Route;
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

            Framework framework = new Framework(this,
                    mapFile.getAbsolutePath(),
                    styleFile.getAbsolutePath(),
                    fontFile.getAbsolutePath(),
                    2500,
                    5000);

            framework.insertPushPin(13.3662d,52.5102d, CoordType.Degree,
                    null,null,0,0);
            framework.insertPushPin(13.44d,52.5102d, CoordType.Degree,
                    null,null,0,0);


            /*
                Alternatively we can also create route using startNavigation methode,
                This methode draws and starts the navigation on map
            * */
//            framework.startNavigation(new double[]{13.3662d,13.4262d,13.44d},
//                    new double[]{52.5102d,52.5102d,52.5102d},
//                    CoordType.Degree);


            frameLayout.addView(new MapView(this, framework));
            drawRoute(framework);
            
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

    private void drawRoute(Framework framework){
            /*

            Create navigation object using createRoute methode
            Draw this route on map using userRoute methode
            * */
        RouteProfile routeProfile = new RouteProfile(RouteProfileType.Car);
        Route route = framework.createRoute(routeProfile, new double[]{13.3662d,13.44d},
                new double[]{52.5102d,52.5102d}, CoordType.Degree);
        framework.useRoute(route, true);
    }
}
