package com.otaliastudios.cameraview.demo;

import android.content.pm.PackageManager;
import android.graphics.PointF;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.otaliastudios.cameraview.CameraException;
import com.otaliastudios.cameraview.CameraListener;
import com.otaliastudios.cameraview.CameraOptions;
import com.otaliastudios.cameraview.CameraView;
import com.otaliastudios.cameraview.PictureResult;
import com.otaliastudios.cameraview.VideoResult;
import com.otaliastudios.cameraview.controls.Engine;
import com.otaliastudios.cameraview.controls.Facing;
import com.otaliastudios.cameraview.controls.Flash;
import com.otaliastudios.cameraview.controls.Grid;
import com.otaliastudios.cameraview.controls.Hdr;
import com.otaliastudios.cameraview.controls.Mode;
import com.otaliastudios.cameraview.controls.PictureFormat;
import com.otaliastudios.cameraview.controls.Preview;
import com.otaliastudios.cameraview.controls.WhiteBalance;
import com.otaliastudios.cameraview.gesture.Gesture;
import com.otaliastudios.cameraview.gesture.GestureAction;
import com.otaliastudios.cameraview.markers.DefaultAutoFocusMarker;
import com.otaliastudios.cameraview.size.Size;

public class MainActivity extends AppCompatActivity {

    private CameraView mCamera;
    private Button mWhiteBalanceBtn,mFocusBtn,mET_Btn,mISO_Btn,mEV_Btn,mHdrBtn,mFlashBtn,mRawBtn;
    private ImageButton mTakePictureBtn;
    private TextView mCameraInfo;
    private SeekBar mFocusBar, mETBar,mIOSBar,mEVBar;
//    private ImageView mImageView;
//    private boolean isSupportRAW = false;

    private PackageManager packageManager;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mCamera = findViewById(R.id.camera);

        mTakePictureBtn = findViewById(R.id.takePicture);
        mWhiteBalanceBtn = findViewById(R.id.setWB_btn);
        mFocusBtn = findViewById(R.id.setFocus_btn);
        mET_Btn = findViewById(R.id.setET_btn);
        mISO_Btn = findViewById(R.id.setISO_btn);
        mEV_Btn = findViewById(R.id.setEV_btn);

        mFlashBtn = findViewById(R.id.setFlash_btn);
        mHdrBtn = findViewById(R.id.setHDR_btn);
        mRawBtn = findViewById(R.id.setRAW_btn);

        mCameraInfo = findViewById(R.id.textView);

        mFocusBar = findViewById(R.id.focusSeekBar);
        mETBar = findViewById(R.id.etSeekBar);
        mIOSBar = findViewById(R.id.isoSeekBar);
        mEVBar = findViewById(R.id.evSeekBar);

//        mImageView = findViewById(R.id.imageView);

//         packageManager = this.getPackageManager();

//         CameraManager cameraManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
//        try {
//            String[] cameraIds = cameraManager.getCameraIdList();
//            for (String id :cameraIds) {
//                Log.d("Camera","Camera id "+ id);
//                CameraCharacteristics cameraCharacteristics =  cameraManager.getCameraCharacteristics(id);
//                Log.d("Camera","cameraCharacteristics"+ cameraCharacteristics.toString());
//
//                int[] ints = cameraCharacteristics.get(CameraCharacteristics.REQUEST_AVAILABLE_CAPABILITIES);
//                for (int key :ints){
//                    if(key == 3){
//                        isSupportRAW = true;
//                    }
//                    Log.d("Camera","cameraCharacteristics REQUEST_AVAILABLE_CAPABILITIES "+ key);
//
//                }
//            }
//        } catch (CameraAccessException e) {
//            e.printStackTrace();
//        }


        mCamera.setExperimental(true);
        //设置框架引擎
        mCamera.setEngine(Engine.CAMERA2);
        //设置预览方式
        mCamera.setPreview(Preview.GL_SURFACE);
        //设置拍摄模式
        mCamera.setMode(Mode.PICTURE);

        mCamera.setFacing(Facing.BACK);

        //设置屏幕手势缩放
        mCamera.mapGesture(Gesture.PINCH, GestureAction.ZOOM); // Pinch to zoom!
        //设置点击屏幕聚焦
//        mCamera.mapGesture(Gesture.TAP, GestureAction.AUTO_FOCUS);

//        mCamera.mapGesture(Gesture.SCROLL_HORIZONTAL,GestureAction.EXPOSURE_CORRECTION);

        //设置网格
//        mCamera.setGrid(Grid.DRAW_4X4);
        // 关闭测光
        mCamera.setPictureMetering(false);

        //显示camera信息
        updateCameraInfo();


        mEVBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                mCamera.setExposureCorrection(progress);
                updateCameraInfo();
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        mFocusBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                mCamera.setFocusDistance(progress);
                updateCameraInfo();
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
        mIOSBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                mCamera.setSensitivity(progress);
                updateCameraInfo();
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
        mETBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                mCamera.setExposureTime(progress);
                updateCameraInfo();
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        mFocusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mCamera.getAFMode()){
                    mCamera.setAFMode(false);
                }else{
                    mCamera.setAFMode(true);
                }
                updateCameraInfo();
            }


        });
        mISO_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mCamera.getAEMode()){
                    mCamera.setAEMode(false);
                }else{
                    mCamera.setAEMode(true);
                }
                updateCameraInfo();

            }
        });
        mET_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mCamera.getAEMode()){
                    mCamera.setAEMode(false);
                }else{
                    mCamera.setAEMode(true);
                }
                updateCameraInfo();

            }
        });

        mWhiteBalanceBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WhiteBalance currentWhiteBalance = mCamera.getWhiteBalance();
                Log.d("Camera",currentWhiteBalance+"");
                if(currentWhiteBalance.equals(WhiteBalance.AUTO)){
                    //设置白平衡
                    mCamera.setWhiteBalance(WhiteBalance.INCANDESCENT);
                }else if(currentWhiteBalance.equals(WhiteBalance.INCANDESCENT)){
                    mCamera.setWhiteBalance(WhiteBalance.FLUORESCENT);
                }if(currentWhiteBalance.equals(WhiteBalance.FLUORESCENT)){
                    mCamera.setWhiteBalance(WhiteBalance.CLOUDY);
                }if(currentWhiteBalance.equals(WhiteBalance.CLOUDY)){
                    mCamera.setWhiteBalance(WhiteBalance.AUTO);
                }
                mWhiteBalanceBtn.setText(currentWhiteBalance.name());
                updateCameraInfo();
            }
        });
        mFlashBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Camera",mCamera.getFlash()+"");
                if(mCamera.getFlash().equals(Flash.OFF)){
                    //设置闪光灯
                    mCamera.setFlash(Flash.ON);
                }else{
                    mCamera.setFlash(Flash.OFF);
                }
                updateCameraInfo();

            }
        });



        mRawBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if(mCamera.getCameraOptions().getSupportedPictureFormats().contains(PictureFormat.DNG)){
                    mCamera.setPictureFormat(PictureFormat.DNG);
                }
                updateCameraInfo();



            }
        });


        mHdrBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Camera",mCamera.getHdr()+"");
                if(mCamera.getHdr().equals(Hdr.OFF)){
                    //设置闪光灯
                    mCamera.setHdr(Hdr.ON);
                }else{
                    mCamera.setHdr(Hdr.OFF);
                }
                updateCameraInfo();

                //启动相机 系统相机
//                Intent intent = new Intent();
//                intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
////                intent.putExtra(MediaStore.EXTRA_OUTPUT, mImageUri);
//                startActivityForResult(intent, 1);

//                try {
////                    com.huawei.camera / com.android.camera
//                    packageManager.getPackageInfo("com.android.camera",0);
//                    Intent intent = packageManager.getLaunchIntentForPackage("com.android.camera");
//
//                    startActivityForResult(intent,1);
//
//
//                } catch (PackageManager.NameNotFoundException e) {
//                    e.printStackTrace();
//                }


            }
        });
        mTakePictureBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCamera.takePicture();
            }
        });

        mCamera.setLifecycleOwner(this);

        mCamera.setAutoFocusMarker(new DefaultAutoFocusMarker());

        mCamera.addCameraListener(new CameraListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onCameraOpened(@NonNull CameraOptions options) {
                super.onCameraOpened(options);
//                mExposureBar.setMin((int) mCamera.getCameraOptions().getExposureCorrectionMinValue());
//                mExposureBar.setMax((int) mCamera.getCameraOptions().getExposureCorrectionMaxValue());
//                mCamera.setEngine(Engine.CAMERA2);
//                mCamera.setPictureFormat(PictureFormat.DNG);
//                Collection<PictureFormat> formats= mCamera.getCameraOptions().getSupportedPictureFormats();
//                for (PictureFormat format:formats) {
//                    Log.d("Camera", "PictureFormat " +format.toString());
//                }
            }

            @Override
            public void onCameraClosed() {
                super.onCameraClosed();
            }

            @Override
            public void onCameraError(@NonNull CameraException exception) {
                super.onCameraError(exception);
            }

            @Override
            public void onPictureTaken(@NonNull PictureResult result) {
                super.onPictureTaken(result);



                PictureFormat format =  result.getFormat();
                Log.d("camera","tackPictureFormat "+format.name());

                Size picsize = result.getSize();
                Toast.makeText(MainActivity.this, "picsize = "+picsize.toString(), Toast.LENGTH_SHORT).show();
                Log.d("camera","picsize = "+picsize.toString());

//                result.toBitmap(new BitmapCallback() {
//                    @Override
//                    public void onBitmapReady(@Nullable Bitmap bitmap) {
//                        mImageView.setImageBitmap(bitmap);
//                    }
//                });
//                result.toFile();
            }

            @Override
            public void onVideoTaken(@NonNull VideoResult result) {
                super.onVideoTaken(result);
            }

            @Override
            public void onOrientationChanged(int orientation) {
                super.onOrientationChanged(orientation);
            }

            @Override
            public void onAutoFocusStart(@NonNull PointF point) {
                super.onAutoFocusStart(point);
                Log.d("Camera","onAutoFocusStart " + point);

            }

            @Override
            public void onAutoFocusEnd(boolean successful, @NonNull PointF point) {
                super.onAutoFocusEnd(successful, point);
                Log.d("Camera","onAutoFocusEnd "+point);

            }

            @Override
            public void onZoomChanged(float newValue, @NonNull float[] bounds, @Nullable PointF[] fingers) {
                super.onZoomChanged(newValue, bounds, fingers);
                Log.d("Camera","onZoomChanged "+ + newValue +"   bounds "+bounds.toString() +" fingers "+fingers.toString());

            }

            @Override
            public void onExposureCorrectionChanged(float newValue, @NonNull float[] bounds, @Nullable PointF[] fingers) {
                super.onExposureCorrectionChanged(newValue, bounds, fingers);
                Log.d("Camera","onExposureCorrectionChanged "+ newValue +"   bounds "+bounds.toString() +" fingers "+fingers.toString());
            }

            @Override
            public void onVideoRecordingStart() {
                super.onVideoRecordingStart();
            }

            @Override
            public void onVideoRecordingEnd() {
                super.onVideoRecordingEnd();
            }

            @Override
            public void onPictureShutter() {
                super.onPictureShutter();
            }
        });
    }

    private void updateCameraInfo() {
        String camerInfo ="CameraEngine:"+mCamera.getEngine().name()
                +"\n Flash:"+mCamera.getFlash().name()
                +"\n PictureFormat:"+mCamera.getPictureFormat().name()
                +"\n Hdr:"+mCamera.getHdr().name()
                +"\n ExposureCorrection:"+mCamera.getExposureCorrection()
                +"\n S:"+mCamera.getExposureTime()
                +"\n F:"+mCamera.getFocusDistanceValue()
                +"\n ISO:"+mCamera.getSensitivityValue()
                +"\n WB:"+mCamera.getWhiteBalance().name()
                +"\n AF:"+mCamera.getAFMode()
                +"\n AE:"+mCamera.getAEMode();
        mCameraInfo.setText(camerInfo);
        mETBar.setProgress((int) mCamera.getExposureTime());
//        mEVBar
    }

//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//    }
//
//    @Override
//    protected void onPause() {
//        super.onPause();
//    }
//
//    @Override
//    protected void onResume() {
//        super.onResume();
//    }
}