package com.example.BOOKED;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;


//import com.bumptech.glide.Glide;

import java.util.List;

public class MessageAdapter extends ArrayAdapter<Info> {

    public MessageAdapter(Context context, int resource, List<Info> objects) {
        super(context, resource, objects);
    }

    @Override
    public View getView(int position,  View convertView, ViewGroup parent) {
        View listitemView = convertView;
        if (convertView == null) {
            listitemView = ((Activity) getContext()).getLayoutInflater().inflate(R.layout.list_item, parent, false);
        }

        Info message = getItem(position);

        //mageView photoImageView = (ImageView) convertView.findViewById(R.id.photoImageView);
        TextView messageTextView = (TextView) listitemView.findViewById(R.id.messageTextView);
        TextView authorTextView = (TextView) listitemView.findViewById(R.id.nameTextView);
        TextView date = (TextView) listitemView.findViewById(R.id.dateTextView);
        TextView time =  (TextView) listitemView.findViewById(R.id.TimeTextView);
        TextView purpose =  (TextView) listitemView.findViewById(R.id.PurposeTextView);
        TextView hall =  (TextView) listitemView.findViewById(R.id.HallTextView);





       /* boolean isPhoto = message.getPhotoUrl() != null;
        if (isPhoto) {
            messageTextView.setVisibility(View.GONE);
            photoImageView.setVisibility(View.VISIBLE);
            Glide.with(photoImageView.getContext())
                    .load(message.getPhotoUrl())
                    .into(photoImageView);
        } else {*/
            String s = "Room: ";
            messageTextView.setText(message.getOrg());
            authorTextView.setText(message.getApprov());
            date.setText(message.getDate());
            time.setText(message.getTime());
            purpose.setText(message.getPurp());
            hall.setText(message.getHall());

          //  messageTextView.setVisibility(View.VISIBLE);
           // photoImageView.setVisibility(View.GONE);
          //  messageTextView.setText(message.getText());
       // }
        //authorTextView.setText(message.getName());

        return listitemView;
    }
}
