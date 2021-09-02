package be.kuleuven.mainactivity.Adapters;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
import be.kuleuven.mainactivity.R;
import be.kuleuven.mainactivity.ModelClasses.Category;


public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {

    Context context;
    List<Category> categoriesList;

    public CategoryAdapter(Context context, List<Category> categoriesList) {
        this.context = context;
        this.categoriesList = categoriesList;
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.categories, parent, false);
        return new CategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {

        holder.imageCategory.setText(categoriesList.get(position).getImageCategory());
        holder.category.setText(categoriesList.get(position).getCategory());
    }

    @Override
    public int getItemCount() {
        return categoriesList.size();
    }

    public static final class CategoryViewHolder extends RecyclerView.ViewHolder
    {
        TextView imageCategory, category;

        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            imageCategory = itemView.findViewById(R.id.txtEmoji);
            category = itemView.findViewById(R.id.txtCategory);
        }
    }

}
