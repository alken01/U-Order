package be.kuleuven.mainactivity.Adapters;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import be.kuleuven.mainactivity.R;
import be.kuleuven.mainactivity.ModelClasses.Item;


public class FeaturedAdapter extends RecyclerView.Adapter<FeaturedAdapter.PopularFoodViewHolder> {

    Context context;
    List<Item> popularFoodList;

    public FeaturedAdapter(Context context, List<Item> popularFoodList) {
        this.context = context;
        this.popularFoodList = popularFoodList;
    }


    @NonNull
    @Override
    public PopularFoodViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.featured, parent, false);
        return new PopularFoodViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PopularFoodViewHolder holder, int position) {

        String setQuantities;
        if (Integer.parseInt(popularFoodList.get(position).getQuantity())>1)
        { setQuantities = popularFoodList.get(position).getQuantity() + " pcs."; }
        else{setQuantities = popularFoodList.get(position).getQuantity() + " pc.";}

        String setToken="";
        int nrOfTokens = Integer.parseInt(popularFoodList.get(position).getToken());
        if(nrOfTokens>=4) { setToken = nrOfTokens + "x🪙"; }
        else { for(int i=0;i<nrOfTokens;i++) { setToken= setToken + "🪙"; }}

        String setOrder;
        if(popularFoodList.get(position).getOrder() == 0)
        { setOrder = ""; }
        else{ setOrder = String.valueOf(popularFoodList.get(position).getOrder()); }

        holder.name.setText(popularFoodList.get(position).getName());
        holder.quantity.setText(setQuantities);
        holder.tokens.setText(setToken);
        holder.description.setText(popularFoodList.get(position).getDescription());
        holder.order.setText(setOrder);
        holder.foodImage.setImageBitmap(popularFoodList.get(position).getImage());
        popularFoodList.get(position).setOrder(popularFoodList.get(position).getOrder());
    }

    @Override
    public int getItemCount() { return popularFoodList.size(); }


    public static final class PopularFoodViewHolder extends RecyclerView.ViewHolder
    {
        ImageView foodImage;
        TextView name, tokens, description, quantity, order;

        public PopularFoodViewHolder(@NonNull View itemView) {
            super(itemView);
            foodImage = itemView.findViewById(R.id.imgRecycle1);
            name = itemView.findViewById(R.id.txtName);
            tokens = itemView.findViewById(R.id.txtTokens);
            description = itemView.findViewById(R.id.txtDescription);
            quantity = itemView.findViewById(R.id.txtQuantity);
            order = itemView.findViewById(R.id.txtOrder);

            itemView.findViewById(R.id.btnPlus).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Integer previous;
                    int quantity = 0;

                    if(order.getText().toString().equals(""))
                    { previous = 0; }
                    else{previous = Integer.parseInt(order.getText().toString());}

                    quantity = previous + 1;
                    order.setText(Integer.toString(quantity));
                }
            });




        }
    }

}
