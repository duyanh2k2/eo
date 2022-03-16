package com.example.myapplication;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ListView listView;
    FloatingActionButton add;
    ArrayList<GhiChu> ls=new ArrayList<>();
    GhiChuAdapter ghiChuAdapter;
    GhiChuDB ghiChuDB;
    public static final String INSERT_A="insert";
    public static final String UPDATE="update";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView=findViewById(R.id.lvghichu);
        add=findViewById(R.id.add);
        getlsGhiChu();
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,MainActivity2.class);
                intent.setAction(INSERT_A);
                AResult.launch(intent);
            }
        });
        registerForContextMenu(listView);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        if(v.getId()==R.id.lvghichu){
            getMenuInflater().inflate(R.menu.menu,menu);
        }
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        AdapterView.AdapterContextMenuInfo acmi=(AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int ps=acmi.position;
        GhiChu ghiChu = ghiChuAdapter.getItem(ps);
        switch (item.getItemId()){
            case R.id.upd:
                Toast.makeText(MainActivity.this,"update ghi chu: "+ ghiChu.getTieude(),Toast.LENGTH_LONG);
              Intent intent=new Intent(MainActivity.this,MainActivity2.class);
              Bundle bundle=new Bundle();
              bundle.putSerializable("ghiChu",ghiChu);
              intent.putExtras(bundle);
              intent.setAction(UPDATE);
              UResult.launch(intent);
              break;
            case R.id.del:
                Toast.makeText(MainActivity.this, "delete ghi chu: " + ghiChu.getTieude(),
                        Toast.LENGTH_LONG).show();
                AlertDialog.Builder delDialog= new AlertDialog.Builder(MainActivity.this);
                delDialog.setTitle("Xoa ghi chu...");
                delDialog.setCancelable(true);
                delDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(MainActivity.this,"You choose cancel button",Toast.LENGTH_LONG).show();
                        dialogInterface.cancel();
                    }
                });
                delDialog.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        ghiChuDB =new GhiChuDB(MainActivity.this);
                        ghiChuDB.delGhiChu(ghiChu.getTieude());
                        dialogInterface.dismiss();
                        getlsGhiChu();
                    }
                });
                AlertDialog alertDialog=delDialog.create();
                alertDialog.show();
                break;
        }
        return true;
    }

    private void getlsGhiChu(){
        ghiChuDB=new GhiChuDB(MainActivity.this);
        ls=ghiChuDB.getALLGhiChu();
        ghiChuAdapter=new GhiChuAdapter(MainActivity.this,0,ls);
        listView.setAdapter(ghiChuAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getlsGhiChu();
    }

    ActivityResultLauncher<Intent> AResult= registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if(result.getResultCode()==RESULT_OK){
                        Intent data=result.getData();
                        String tieude=data.getStringExtra("tieude");
                        String noidung=data.getStringExtra("noidung");
                        String ngay=data.getStringExtra("ngaythang");
                        ghiChuDB=new GhiChuDB(MainActivity.this);
                        ghiChuDB.insGhichu(new GhiChu(tieude,ngay,noidung));
                        Toast.makeText(MainActivity.this,"["+tieude+": "+noidung+" "+ngay+"]",Toast.LENGTH_LONG).show();

                    }
                }
            });
    ActivityResultLauncher<Intent> UResult= registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if(result.getResultCode()==RESULT_OK){
                        Intent data=result.getData();
                        String tieude=data.getStringExtra("tieude");
                        String noidung=data.getStringExtra("noidung");
                        String ngay=data.getStringExtra("ngaythang");
                        ghiChuDB=new GhiChuDB(MainActivity.this);
                        ghiChuDB.updateGhichu(new GhiChu(tieude,ngay,noidung),data.getStringExtra("key"));
                        Toast.makeText(MainActivity.this,"["+tieude+": "+noidung+" "+ngay+"]",Toast.LENGTH_LONG).show();

                    }
                }
            });
}