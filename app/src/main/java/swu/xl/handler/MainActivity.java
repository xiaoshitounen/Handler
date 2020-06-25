package swu.xl.handler;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.lang.ref.WeakReference;

public class MainActivity extends AppCompatActivity {

    private static final int MESSAGE_CODE = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //处理消息
        final Handler handler = new MyHandler(this);

        //发送消息
        findViewById(R.id.send).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //创建消息
                Message message = handler.obtainMessage();
                message.arg1 = 1;
                message.arg2 = 2;
                message.obj = "message";
                message.what = MESSAGE_CODE;
                //发送消息
                handler.sendMessage(message);

            }
        });
    }

    /**
     * 自定义的解决内存泄漏的Handler
     */
    private static class MyHandler extends Handler{

        //定义弱引用实例
        private WeakReference<Activity> reference;

        //构造方法中传入所需的Activity
        public MyHandler(Activity activity) {
            //使用WeakReference弱引用持有Activity实例
            this.reference = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);

            //接受消息
            switch (msg.what){
                case MESSAGE_CODE:
                    Activity main = reference.get();
                    Toast.makeText(main, (String) msg.obj, Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    }

}
