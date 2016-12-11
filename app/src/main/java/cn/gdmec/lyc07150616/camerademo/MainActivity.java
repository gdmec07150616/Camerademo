package cn.gdmec.lyc07150616.camerademo;
import android.hardware.Camera;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class MainActivity extends AppCompatActivity implements SurfaceHolder.Callback {
    private ImageView imageview;
    private File file;
    private Camera camera;
    private Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SurfaceView mSurfaceView =(SurfaceView) this.findViewById(R.id.surfaceView1);
        SurfaceHolder mSurfaceHolder=mSurfaceView.getHolder();
        mSurfaceHolder.addCallback(this);
        mSurfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
    }
    public  void  takePhoto(View v){

        camera.takePicture(null,null,pictureCallback);
    }
    @Override
    public void surfaceCreated(SurfaceHolder holder) {
    camera=camera.open();
        android.hardware.Camera.Parameters params=camera.getParameters();
        params.setFocusMode(Camera.Parameters.FOCUS_MODE_AUTO);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

        try {
            camera.setPreviewDisplay(holder);
        }catch (IOException exception) {
            camera.release();
            camera = null;
        }
        camera.startPreview();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }
    Camera.PictureCallback pictureCallback=new Camera.PictureCallback(){
        @Override
        public void onPictureTaken(byte[] data,Camera camera){
            if(data!=null){
                savePicture(data);
            }

        }
    };
public  void savePicture(byte[] data){
    try{
        String imageId=System.currentTimeMillis()+"";
        String pathName=android.os.Environment.getExternalStorageDirectory().getPath()+"/";
        File file=new File(pathName);
        if(!file.exists()){
            file.createNewFile();
        }
        FileOutputStream fos=new FileOutputStream(file);
        fos.write(data);
        fos.close();
        Toast.makeText(this, "已经保存在路径："+pathName, Toast.LENGTH_LONG).show();
    }catch (Exception e){
        e.printStackTrace();
    }
}
}
