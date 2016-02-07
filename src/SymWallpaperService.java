package pontus.symmetry;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.os.Handler;
import android.preference.Preference;
import android.preference.PreferenceManager;
import android.service.wallpaper.WallpaperService;
import android.view.Display;
import android.view.SurfaceHolder;
import android.view.WindowManager;

/**
 * Created by Pontus on 2015-12-17.
 */
public class SymWallpaperService extends WallpaperService {

    @Override
    public Engine onCreateEngine() {
        PreferenceManager.setDefaultValues(this, R.xml.sym_settings, false);
        return new SymWallpaperEngine();
    }

    private class SymWallpaperEngine extends Engine
            implements SharedPreferences.OnSharedPreferenceChangeListener {

        private Paint paint;
        private int width;
        private int height;
        private final int delay = 10;
        private MovingGraph graph;
        private boolean isVisible = false;
        private final Handler handler = new Handler();
        private final Runnable drawRunner = new Runnable() {
            @Override
            public void run() {
                draw();
            }
        };

        public SymWallpaperEngine() {
            initPaint();
            initGraph();
            handler.post(drawRunner);
        }

        private void initPaint() {
            paint = new Paint();
            paint.setAntiAlias(true);

            Display d = ((WindowManager)getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
            Point size = new Point();
            d.getSize(size);
            width = size.x;
            height = size.y;
        }

        private void initGraph(){

            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(SymWallpaperService.this);
            prefs.registerOnSharedPreferenceChangeListener(this);
            //onSharedPreferenceChanged(prefs, null);

            graph = new MovingGraph(width/2, 0);
            graph.createStdGraph(width, height);
            graph.setColorChange(prefs.getBoolean("change_colors", true));
            graph.setThickness(Float.parseFloat(prefs.getString("thickness_choice", "2"))/10);
            graph.setColor(Integer.parseInt(prefs.getString("color_choice", "0")));
        }

        private void draw() {
            SurfaceHolder holder = getSurfaceHolder();

            Canvas canvas = null;
            try {
                canvas = holder.lockCanvas();
                if (canvas != null) {

                    paint.setColor(Color.rgb(46, 46, 45));
                    canvas.drawRect(0, 0, width, height, paint);

                    for(int i = 0; i < graph.getEdges().length; i++){
                        paint.setStrokeWidth(graph.getThickness(i));
                        graph.getEdge(i).changeColor();

                        paint.setColor(Color.argb(graph.getEdge(i).getAlpha(),
                                graph.getEdge(i).getR(), graph.getEdge(i).getG(),
                                graph.getEdge(i).getB()));

                        canvas.drawLine(graph.getEdgeX(i, 0), graph.getEdgeY(i, 0),
                                graph.getEdgeX(i, 1), graph.getEdgeY(i, 1), paint);
                    }
                }
            } finally {
                if (canvas != null)
                    holder.unlockCanvasAndPost(canvas);
            }
            if (isVisible)
            {
                handler.postDelayed(drawRunner, delay);
            }

        }

        public void onSharedPreferenceChanged(SharedPreferences prefs, String key) {
            graph.setColorChange(prefs.getBoolean("change_colors", true));
            graph.setThickness(Float.parseFloat(prefs.getString("thickness_choice", "2"))/10);
            graph.setColor(Integer.parseInt(prefs.getString("color_choice", "0")));
        }

        @Override
        public void onSurfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            /*this.width = width;
            this.height = height;
            super.onSurfaceChanged(holder, format, width, height);
        */
            draw();
        }

        @Override
        public void onVisibilityChanged(boolean visible) {
            isVisible = visible;
            if (visible) {
                draw();
            } else {
                handler.removeCallbacks(drawRunner);
            }
        }

        @Override
        public void onSurfaceDestroyed(SurfaceHolder holder) {
            super.onSurfaceDestroyed(holder);
            isVisible = false;
            handler.removeCallbacks(drawRunner);
        }

        @Override
        public void onDestroy() {
            super.onDestroy();
            isVisible = false;
            handler.removeCallbacks(drawRunner);
        }
    }
}
