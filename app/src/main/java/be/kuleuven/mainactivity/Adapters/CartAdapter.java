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

import be.kuleuven.mainactivity.Activities.BasketActivity;
import be.kuleuven.mainactivity.ModelClasses.Item;
import be.kuleuven.mainactivity.R;
import be.kuleuven.mainactivity.database.DatabaseCart;


public class CartAdapter extends RecyclerView.Adapter<CartAdapter.MenuViewHolder> {

    Context context;
    List<Item> menuList;


    public CartAdapter(Context context, List<Item> popularFoodList) {
        this.context = context;
        this.menuList = popularFoodList;
    }


    @NonNull
    @Override
    public MenuViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.cart, parent, false);
        return new MenuViewHolder(view);
    }



    @Override
    public void onBindViewHolder(@NonNull MenuViewHolder holder, @SuppressLint("RecyclerView") int position) {

        String setQuantities;
        if (Integer.parseInt(menuList.get(position).getQuantity())>1){
            setQuantities = menuList.get(position).getQuantity() + " pcs."; }
        else{setQuantities = menuList.get(position).getQuantity() + " pc.";}

        String setToken="";
        int nrOfTokens = Integer.parseInt(menuList.get(position).getToken());
        if(nrOfTokens>=4) { setToken = nrOfTokens + "x🪙"; }
        else { for(int i=0;i<nrOfTokens;i++) { setToken = setToken + "🪙"; }}

        String setOrder = String.valueOf(menuList.get(position).getOrder()) + "x";



        holder.itemView.findViewById(R.id.btnPlus2cart).setOnClickListener(new View.OnClickListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onClick(View v) {
                Item item;
                DatabaseCart databaseCart = new DatabaseCart(v.getContext());
                if(databaseCart.checkIfItemExists(menuList.get(position).getName())){

                    int orderNumber = 1 + menuList.get(position).getOrder();
                    item = new Item(menuList.get(position).getImage(),
                            menuList.get(position).getName(),
                            menuList.get(position).getToken(),
                            menuList.get(position).getQuantity(),
                            menuList.get(position).getDescription(),
                            orderNumber);
                    databaseCart.updateItem(item);
                }
                else{
                    item = new Item(menuList.get(position).getImage(),
                            menuList.get(position).getName(),
                            menuList.get(position).getToken(),
                            menuList.get(position).getQuantity(),
                            menuList.get(position).getDescription(),
                            1);
                    databaseCart.addItem(item);
                }
                updateItems(databaseCart.getAllItems());
            }
        });

        holder.itemView.findViewById(R.id.btnMinus2cart).setOnClickListener(new View.OnClickListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onClick(View v) {
                Item item;
                DatabaseCart databaseCart = new DatabaseCart(v.getContext());
                if(menuList.get(position).getOrder() == 1) {
                    databaseCart.removeItem(menuList.get(position).getName());
                }
                else{
                    int orderNumber = menuList.get(position).getOrder() - 1;
                    item = new Item(menuList.get(position).getImage(),
                            menuList.get(position).getName(),
                            menuList.get(position).getToken(),
                            menuList.get(position).getQuantity(),
                            menuList.get(position).getDescription(),
                            orderNumber);
                    databaseCart.addItem(item);
                }
                updateItems(databaseCart.getAllItems());
            }
        });

        holder.foodImage.setImageBitmap(menuList.get(position).getImage());
        holder.name.setText(menuList.get(position).getName());
        holder.quantity.setText(setQuantities);
        holder.tokens.setText(setToken);
        holder.description.setText(menuList.get(position).getDescription());
        holder.order.setText(setOrder);
//        menuList.get(position).setOrder(menuList.get(position).getOrder());

    }

    @Override
    public int getItemCount() {
        return menuList.size();
    }

    public static final class MenuViewHolder extends RecyclerView.ViewHolder
    {
        ImageView foodImage;
        TextView name, tokens, description, quantity,order;

        public MenuViewHolder(@NonNull View itemView) {
            super(itemView);
            foodImage = itemView.findViewById(R.id.imgRecycle2cart);
            name = itemView.findViewById(R.id.txtName2cart);
            tokens = itemView.findViewById(R.id.txtTokens2cart);
            description = itemView.findViewById(R.id.txtDescription2cart);
            quantity = itemView.findViewById(R.id.txtQuantity2cart);
            order = itemView.findViewById(R.id.textOrderCart);

        }
    }
    public void updateItems(List<Item> newList) {
        menuList.clear();
        menuList.addAll(newList);
        this.notifyDataSetChanged();
    }

}
