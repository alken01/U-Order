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


public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.MenuViewHolder> {

    Context context;
    List<Item> menuList;


    public MenuAdapter(Context context, List<Item> popularFoodList) {
        this.context = context;
        this.menuList = popularFoodList;
    }


    @NonNull
    @Override
    public MenuViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.menu, parent, false);
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
        else { for(int i=0;i<nrOfTokens;i++) { setToken= setToken + "🪙"; }}

        holder.itemView.findViewById(R.id.btnPlus2).setOnClickListener(new View.OnClickListener() {
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
            }
        });

//                updateItems(databaseCart.getAllItems());
//                Integer previous;
//                int quantity = 0;
//                if(order.getText().toString() == "")
//                { previous = 0; }
//                else{previous = Integer.parseInt(order.getText().toString());}
//                quantity = previous + 1;
//                order.setText(Integer.toString(quantity));



        String setOrder;
        if(menuList.get(position).getOrder() == 0) { setOrder = ""; }
        else{ setOrder = String.valueOf(menuList.get(position).getOrder()); }

        holder.foodImage.setImageBitmap(menuList.get(position).getImage());
        holder.name.setText(menuList.get(position).getName());
        holder.quantity.setText(setQuantities);
        holder.tokens.setText(setToken);
        holder.description.setText(menuList.get(position).getDescription());
        menuList.get(position).setOrder(menuList.get(position).getOrder());

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
            foodImage = itemView.findViewById(R.id.imgRecycle2);
            name = itemView.findViewById(R.id.txtName2);
            tokens = itemView.findViewById(R.id.txtTokens2);
            description = itemView.findViewById(R.id.txtDescription2);
            quantity = itemView.findViewById(R.id.txtQuantity2);
//            order = itemView.findViewById(R.id.txtOrder2);
        }
    }

    public void updateItems(List<Item> newList) {
        menuList.clear();
        menuList.addAll(newList);
        this.notifyDataSetChanged();
    }

}
