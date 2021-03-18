package com.xhwl.xiyi.qq;
  
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
  
import android.graphics.Canvas;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xhwl.xiyi.R;

import java.util.ArrayList;
import java.util.List;
  
/**
 * @author Huang xudong
 * @date 2020/7/26
 */
public class MainActivity extends AppCompatActivity {
 private RecyclerView recyclerView;
  
 private List list=new ArrayList();
  
 class MyAdpter extends RecyclerView.Adapter<MyAdpter.ViewHolder>{
  
  class ViewHolder extends RecyclerView.ViewHolder{
   private TextView textView;
   private LinearLayout linearLayout;
  
   public ViewHolder(@NonNull View itemView) {
    super(itemView);
    linearLayout=itemView.findViewById(R.id.ll_main);
    textView=itemView.findViewById(R.id.tv_main);
   }
  }
  
  @NonNull
  @Override
  public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
   View inflate = LayoutInflater.from(getApplicationContext()).inflate(R.layout.item_demo,parent, false);
   return new ViewHolder(inflate);
  }
  
  @Override
  public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
  
  }
  
  @Override
  public int getItemCount() {
   return list.size();
  }
 }
  
 class CallBack extends ItemTouchHelper.Callback{
  @Override
  public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
   return makeMovementFlags(0,ItemTouchHelper.LEFT);
  }
  
  @Override
  public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
   return false;
  }
  
  @Override
  public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
   /**
    * call max distance start onSwiped call
    */
  }
  
  @Override
  public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
  
  
   if (actionState==ItemTouchHelper.ACTION_STATE_SWIPE){
    /**
     * get {@link TextView#getWidth()}
     */
    ViewGroup viewGroup= (ViewGroup) viewHolder.itemView;
    TextView textView = (TextView) viewGroup.getChildAt(1);
    ViewGroup.LayoutParams layoutParams = textView.getLayoutParams();
    if (Math.abs(dX)<=layoutParams.width){
     /**
      * move {@link RecyclerView.ViewHolder} distance
      */
     viewHolder.itemView.scrollTo((int) -dX,0);
     /**
      * callAction or register click bind view
      */
    }
   }
  }
 }
  
 @Override
 protected void onCreate(Bundle savedInstanceState) {
  super.onCreate(savedInstanceState);
  setContentView(R.layout.activity_main_qq);
  
  recyclerView=findViewById(R.id.rv_main);
  list.add(1);
  list.add("2");
  MyAdpter myAdpter=new MyAdpter();
  LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getApplicationContext());
  linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
  recyclerView.setLayoutManager(linearLayoutManager);
  recyclerView.setAdapter(myAdpter);
  ItemTouchHelper itemTouchHelper=new ItemTouchHelper(new CallBack());
  itemTouchHelper.attachToRecyclerView(recyclerView);
 }
}