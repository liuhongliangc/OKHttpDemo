package liuhongliang.bawei.com.okhttpdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    public static final String url="http://result.eolinker.com/y7fna4B18728b8965aa53caf37032864df9d353ca6f28ed?uri=hongliang";
    public static final String url2="http://result.eolinker.com/gfGTLlHc049c6b450500b16971f52bd8e83f6b2fed305ab";
    public static final String url3="http://admin.wap.china.com/user/NavigateTypeAction.do?processID=getNavigateNews";
    private Button button;
    private Button button2;
    private Button button3;
    private Button button4;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();

    }
    private void initView() {
        button = (Button) findViewById(R.id.button);
        button2 = (Button) findViewById(R.id.button2);
        button3 = (Button) findViewById(R.id.button3);
        button4 = (Button) findViewById(R.id.button4);

        button.setOnClickListener(this);
        button2.setOnClickListener(this);
        button3.setOnClickListener(this);
        button4.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button:
                getSynchronizationHttp();
                break;
            case R.id.button2:
                getAsyncHttp();
                break;
            case R.id.button3:
                postSynchronizationHttp();
                break;
            case R.id.button4:
                postAsyncHttp();
                break;
        }
    }
    //GET同步解析
    public void getSynchronizationHttp(){
        new Thread(){
            @Override
            public void run() {
                super.run();
                OkHttpClient okHttpClient = new OkHttpClient();
                Request.Builder builder = new Request.Builder().url(MainActivity.url);
                builder.method("GET",null);
                Request request = builder.build();
                Call call = okHttpClient.newCall(request);
                try {
                    Response execute = call.execute();
                    Log.d("getSynchronizationHttp",execute.body().string());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(MainActivity.this,"GET同步请求成功",Toast.LENGTH_SHORT).show();
                        }
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();



    }
    //GET异步解析
    public void getAsyncHttp() {
        OkHttpClient okHttpClient = new OkHttpClient();
        Request.Builder builder = new Request.Builder().url(url);
        builder.method("GET", null);
        Request request = builder.build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String string = response.body().string();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(MainActivity.this,"GET异步请求成功",Toast.LENGTH_SHORT).show();
                    }
                });
                Log.d("getAsyncHttp", string);
            }
        });
    }
    public void postSynchronizationHttp(){
        new Thread(){
            @Override
            public void run() {
                super.run();
                OkHttpClient okHttpClient = new OkHttpClient();
                FormBody body = new FormBody.Builder()
                        .add("page", "1")
                        .add("code", "news")
                        .add("pageSize", "20")
                        .add("parentid", "0")
                        .add("type", "1")
                        .build();
                Request.Builder builder = new Request.Builder().url(MainActivity.url3);
                Request request = builder
                        .post(body)
                        .build();
                Call call = okHttpClient.newCall(request);
                try {
                    Response response = call.execute();
                    Log.d("postSynchronizationHttp", response.body().string());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(MainActivity.this,"POST同步请求成功",Toast.LENGTH_SHORT).show();
                        }
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();


    }
    public void postAsyncHttp(){
        OkHttpClient okHttpClient = new OkHttpClient();
        FormBody body = new FormBody.Builder()
                .add("page", "1")
                .add("code", "news")
                .add("pageSize", "20")
                .add("parentid", "0")
                .add("type", "1")
                .build();
        Request request = new Request.Builder()
                .url(url3)
                .post(body)
                .build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.d("postAsyncHttp", response.body().string());
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(MainActivity.this,"POST异步请求成功",Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });


    }
}
