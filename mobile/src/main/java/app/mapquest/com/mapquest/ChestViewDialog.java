package app.mapquest.com.mapquest;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.parse.ParseException;

import java.util.ArrayList;
import java.util.List;

import app.mapquest.com.mapquest.api.Getting;
import app.mapquest.com.mapquest.data.LocationInfo;

public class ChestViewDialog extends DialogFragment {

    static ChestViewDialog newInstance(String gameName) {
        ChestViewDialog f = new ChestViewDialog();

        // Supply num input as an argument.
        Bundle args = new Bundle();
        args.putString("GAME", gameName);
        f.setArguments(args);

        return f;
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Log.i("ChestDiag", "onCreateDialog");
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();

        String gameName = getArguments().getString("GAME");
        List<LocationInfo> tempVars;
        try {
            tempVars = Getting.getAllGamesLocationInfos(gameName);
        } catch (ParseException e) {
            tempVars = new ArrayList<>();
            e.printStackTrace();
        }

        final List<LocationInfo> locationInfos = tempVars;
        ListAdapter adapter = new ArrayAdapter<LocationInfo>(
                getActivity().getApplicationContext(), R.layout.chest_row, locationInfos) {

            ViewHolder holder;

            class ViewHolder {
                TextView chestDescription;
                TextView chestScore;
            }

            public View getView(int position, View convertView,
                                ViewGroup parent) {
                final LayoutInflater inflater = (LayoutInflater) getActivity().getApplicationContext()
                        .getSystemService(
                                Context.LAYOUT_INFLATER_SERVICE);

                if (convertView == null) {
                    convertView = inflater.inflate(
                            R.layout.chest_row, null);

                    holder = new ViewHolder();
                    holder.chestDescription = (TextView) convertView
                            .findViewById(R.id.chest_description);
                    holder.chestScore = (TextView) convertView
                            .findViewById(R.id.chest_score);
                    convertView.setTag(holder);
                } else {
                    // view already defined, retrieve view holder
                    holder = (ViewHolder) convertView.getTag();
                }

                holder.chestDescription.setText(locationInfos.get(position).getDescription());
                holder.chestScore.setText(String.valueOf(locationInfos.get(position).getScore()));

                return convertView;
            }
        };
        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setView(inflater.inflate(R.layout.dialog_chest_list, null))
                .setTitle(R.string.my_chases)
                .setAdapter(adapter, null);


        AlertDialog result = builder.create();

        //need to get the listview and manually fuck it to not be selectable
        result.getListView().setClickable(false);
        return result;
    }
}