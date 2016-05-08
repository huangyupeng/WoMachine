package com.example.peng.graduationproject.ui.personal;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.peng.graduationproject.R;
import com.example.peng.graduationproject.common.BaseActivity;
import com.example.peng.graduationproject.common.Constants;
import com.example.peng.graduationproject.common.NetManager;
import com.example.peng.graduationproject.model.User;
import com.example.peng.graduationproject.model.UserInfoManager;
import com.example.peng.graduationproject.ui.dialog.EditDialog;

import java.io.File;

/**
 * Created by peng on 2016/4/23.
 */
public class PersonalInfoActivity extends BaseActivity implements View.OnClickListener{

    private LinearLayout face;
    private ImageView current_face;
    private LinearLayout name;
    private TextView current_name;

    private User currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentLayout(R.layout.activity_personalinfo);
        setTitleText("个人信息");
        getBtn_back().setVisibility(View.VISIBLE);

        initView();
        setDefaultValues();
        bindEvents();
    }

    @Override
    protected void initView() {


        face = (LinearLayout)findViewById(R.id.face);
        current_face = (ImageView)findViewById(R.id.currentface);
        name = (LinearLayout)findViewById(R.id.name);
        current_name = (TextView)findViewById(R.id.currentname);
    }

    @Override
    protected void setDefaultValues() {
        currentUser = UserInfoManager.getCurrentUser(PersonalInfoActivity.this);

        //TEST NO LOGIN

        currentUser = new User();
        currentUser.setName("huanyupeng");
        currentUser.setId("15356262439");

        //TEST NO LOGIN


        if (currentUser == null){
            showToast("连接失败，请尝试重新登录");
        }else {
            current_name.setText(currentUser.getName());

            try {
                Bitmap bitmap = null;
                File file = new File(Constants.FILE_DIRECTORY + "/"
                        + currentUser.getId() + "/face.jpg");

                if (file.exists()) {
                    bitmap = BitmapFactory.decodeFile(Constants.FILE_DIRECTORY + "/"
                            + currentUser.getId() + "/face.jpg");
                    current_face.setImageBitmap(bitmap);
                } else
                    current_face.setImageResource(R.mipmap.defaultface);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    protected void bindEvents() {

        face.setOnClickListener(this);
        name.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.face:

                LayoutInflater layoutInflater = LayoutInflater
                        .from(PersonalInfoActivity.this);
                View dlgview = layoutInflater.inflate(R.layout.face_dlg, null);

                final AlertDialog dialog=new AlertDialog.Builder(PersonalInfoActivity.this).setView(dlgview)
                        .create();
                dialog.setView(dlgview, 0, 0, 0, 0);
                dialog.show();

                LinearLayout op1 = (LinearLayout) dlgview.findViewById(R.id.op1);
                LinearLayout op2 = (LinearLayout) dlgview.findViewById(R.id.op2);
                op1.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View arg0) {

                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        intent.putExtra(
                                MediaStore.EXTRA_OUTPUT,
                                Uri.fromFile(new File(Constants.FILE_DIRECTORY, "tempphoto.jpg")));
                        startActivityForResult(intent, 1);
                        dialog.dismiss();
                    }

                });
                op2.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View arg0) {

                        Intent intent = new Intent(Intent.ACTION_PICK,null);
                        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                                "image/*");
                        startActivityForResult(intent, 2);
                        dialog.dismiss();
                    }
                });
                break;
            case R.id.name:
                EditDialog editdialog = new EditDialog(PersonalInfoActivity.this);

                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode != 100) {
            return;
        }

        if (requestCode == 2) {
            if (data.getData()==null)
                return;
            Uri uri=data.getData();
            if (uri==null)
                return;

            System.out.println(uri);
            startPhotoZoom(uri);

        } else if (requestCode == 1) {
            Uri uri = Uri.fromFile(new File(Constants.FILE_DIRECTORY+"/tempphoto.jpg"));
            startPhotoZoom(uri);

        }
        else if (requestCode==3){


            try{
                if (!NetManager.isConnect(PersonalInfoActivity.this)){
                    showToast("网络状况不佳");
                    return;
                }

                String filepath=Constants.FILE_DIRECTORY + "/" + currentUser.getId() + "/face.jpg";

                //TODO send image
                Bitmap cropBitmap = BitmapFactory.decodeFile(filepath);

                if (cropBitmap != null) {
                    //ImageView riv=(ImageView)ChildInfoActivity.this.findViewById(R.id.currentface);
                    current_face.setImageBitmap(cropBitmap);

                }
            }catch(Exception e){
                e.printStackTrace();
                System.out.println("EXCEPTION");
            }

        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void startPhotoZoom(Uri uri) {
        int  dp = 100;

        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        // 下面这个crop=true是设置在开启的Intent中设置显示的VIEW可裁剪
        intent.putExtra("crop", "true");
        intent.putExtra("scale", true);// 去黑边
        intent.putExtra("scaleUpIfNeeded", true);// 去黑边
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);//输出是X方向的比例
        intent.putExtra("aspectY", 1);
        // outputX outputY 是裁剪图片宽高，切忌不要再改动下列数字，会卡死
        intent.putExtra("outputX", dp);//输出X方向的像素
        intent.putExtra("outputY", dp);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        intent.putExtra("noFaceDetection", true);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(Constants.FILE_DIRECTORY + "/" + currentUser.getId(), "face.jpg")));
        intent.putExtra("return-data", false);//设置为不返回数据
        startActivityForResult(intent, 3);
    }
}
