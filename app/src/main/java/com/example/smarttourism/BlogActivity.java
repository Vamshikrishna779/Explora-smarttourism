package com.example.smarttourism;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class BlogActivity extends AppCompatActivity {

    private RecyclerView blogRecyclerView;
    private BlogAdapter blogAdapter;
    private List<BlogModel> blogList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blog);
        ImageButton btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(v -> finish());
        blogRecyclerView = findViewById(R.id.blogRecyclerView);
        blogRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        blogList = new ArrayList<>();
        loadBlogs();

        blogAdapter = new BlogAdapter(blogList);
        blogRecyclerView.setAdapter(blogAdapter);
    }

    private void loadBlogs() {

        blogList.add(new BlogModel(
                "Explora - Smart Tourism Guide",
                "Explora is a GIS-based smart tourism app that enhances travel experiences through AI-powered recommendations and cultural insights.",
                R.drawable.aaab));

        blogList.add(new BlogModel(
                "Indian States Covered in Explora",
                "From Rajasthan to Kerala, explore the rich heritage of India's 28 states through our interactive maps and cultural insights.",
                R.drawable.image1));
        blogList.add(new BlogModel(
                "Telangana – The Pearl City & Beyond",
                "Hyderabad's Charminar, Golconda Fort, and Ramoji Film City offer a perfect blend of heritage and modern attractions.",
                R.drawable.telangana));

        blogList.add(new BlogModel(
                "Andhra Pradesh – The Land of Temples & Beaches",
                "Explore the spiritual Tirupati temple, scenic Araku Valley, and the vibrant city of Visakhapatnam.",
                R.drawable.andhra_pradesh));

        blogList.add(new BlogModel(
                "Rajasthan – The Land of Kings",
                "Discover Jaipur, Jodhpur, Udaipur, and the golden sands of Jaisalmer in this royal state of India.",
                R.drawable.rajasthan));

        blogList.add(new BlogModel(
                "Kerala – God’s Own Country",
                "Experience Munnar, Alleppey’s backwaters, Kochi’s history, and the lush green landscapes of Kerala.",
                R.drawable.kerala));

        blogList.add(new BlogModel(
                "Goa – The Beach Paradise",
                "From Baga Beach to Dudhsagar Falls, explore Goa’s nightlife, water sports, and Portuguese heritage.",
                R.drawable.goa));

        blogList.add(new BlogModel(
                "Tamil Nadu – The Temple State",
                "Visit the architectural wonders of Madurai, Chennai, and Kanyakumari while enjoying Ooty’s hill station charm.",
                R.drawable.tamil_nadu));

        blogList.add(new BlogModel(
                "Uttarakhand – The Land of Gods",
                "Explore the spiritual retreats of Rishikesh, the serene beauty of Nainital, and the divine Kedarnath.",
                R.drawable.uttarakhand));
    }

    class BlogAdapter extends RecyclerView.Adapter<BlogAdapter.BlogViewHolder> {
        private List<BlogModel> blogs;

        BlogAdapter(List<BlogModel> blogs) {
            this.blogs = blogs;
        }

        @NonNull
        @Override
        public BlogViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = getLayoutInflater().inflate(R.layout.item_blog, parent, false);
            return new BlogViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull BlogViewHolder holder, int position) {
            BlogModel blog = blogs.get(position);
            holder.blogTitle.setText(blog.getTitle());
            holder.blogDesc.setText(blog.getDescription());
            holder.blogImage.setImageResource(blog.getImageRes());

            holder.itemView.setOnClickListener(v -> {
                Intent intent = new Intent(BlogActivity.this, StateActivity.class);
                intent.putExtra("title", blog.getTitle());
                intent.putExtra("desc", blog.getDescription());
                intent.putExtra("imageRes", blog.getImageRes());
                startActivity(intent);
            });
        }

        @Override
        public int getItemCount() {
            return blogs.size();
        }

        class BlogViewHolder extends RecyclerView.ViewHolder {
            TextView blogTitle, blogDesc;
            ImageView blogImage;

            BlogViewHolder(View itemView) {
                super(itemView);
                blogTitle = itemView.findViewById(R.id.blogTitle);
                blogDesc = itemView.findViewById(R.id.blogDesc);
                blogImage = itemView.findViewById(R.id.blogImage);
            }
        }
    }
}
