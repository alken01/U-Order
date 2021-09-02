package be.kuleuven.mainactivity.Adapters;
import android.annotation.SuppressLint;
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
import be.kuleuven.mainactivity.Database.DatabaseCart;


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
    public void onBindViewHolder(@NonNull PopularFoodViewHolder holder, @SuppressLint("RecyclerView") int position) {

        String setQuantities;
//        if (Integer.parseInt(popularFoodList.get(position).getQuantity())>1)
//        { setQuantities = popularFoodList.get(position).getQuantity() + " pcs."; }
//        else{setQuantities = popularFoodList.get(position).getQuantity() + " pc.";}

        String setToken="";
        int nrOfTokens = Integer.parseInt(popularFoodList.get(position).getToken());
        if(nrOfTokens>=4) { setToken = nrOfTokens + "x🪙"; }
        else { for(int i=0;i<nrOfTokens;i++) { setToken= setToken + "🪙"; }}

        String setOrder;
        if(popularFoodList.get(position).getOrder() == 0)
        { setOrder = ""; }
        else{ setOrder = String.valueOf(popularFoodList.get(position).getOrder()); }

        holder.name.setText(popularFoodList.get(position).getName());

//        holder.quantity.setText(popularFoodList.get(position).getQuantity());
        holder.tokens.setText(setToken);
        holder.description.setText(popularFoodList.get(position).getDescription());
        holder.order.setText(setOrder);
        holder.foodImage.setImageBitmap(popularFoodList.get(position).getImage());
        popularFoodList.get(position).setOrder(popularFoodList.get(position).getOrder());

        holder.itemView.findViewById(R.id.btnPlus).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Item item;
                DatabaseCart databaseCart = new DatabaseCart(v.getContext());
                if(databaseCart.checkIfItemExists(popularFoodList.get(position).getName())){

                    int orderNumber = 1 + popularFoodList.get(position).getOrder();
                    item = new Item(popularFoodList.get(position).getImage(),
                            popularFoodList.get(position).getName(),
                            popularFoodList.get(position).getToken(),
                            popularFoodList.get(position).getQuantity(),
                            popularFoodList.get(position).getDescription(),
                            orderNumber);
                    databaseCart.updateItem(item);
                }
                else{
                    int orderNumber = 1 + popularFoodList.get(position).getOrder();
                    item = new Item(popularFoodList.get(position).getImage(),
                            popularFoodList.get(position).getName(),
                            popularFoodList.get(position).getToken(),
                            popularFoodList.get(position).getQuantity(),
                            popularFoodList.get(position).getDescription(),
                            1);
                    databaseCart.addItem(item);
                }
            }
        });
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
        }
    }


}
