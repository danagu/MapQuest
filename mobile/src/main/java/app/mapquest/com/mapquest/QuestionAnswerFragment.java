package app.mapquest.com.mapquest;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link QuestionAnswerFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link QuestionAnswerFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class QuestionAnswerFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    public static final String QTN_TXT= "QuestionText";
    public static final String ANS_TXT = "AnswerText";
    public static final String CHST_TXT = "ChestText";
    public static final String GAME_NAME = "GameName";



    private String mGameName;
    private String mChestText;
    private String mQuestionTxt;
    private String mAnswerText;

    private OnFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param gameName Game name for lookup
     * @param chestText Chest description text, partially redundant
     * @param questionTxt question text, partially redundant
     * @param answerText answer text, partially redundant
     * @return A new instance of fragment QuestionAnswerFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static QuestionAnswerFragment newInstance(String gameName,String chestText,String questionTxt, String answerText) {
        QuestionAnswerFragment fragment = new QuestionAnswerFragment();
        Bundle args = new Bundle();
        args.putString(QTN_TXT, questionTxt);
        args.putString(CHST_TXT, chestText);
        args.putString(ANS_TXT, answerText);
        args.putString(GAME_NAME, gameName);
        fragment.setArguments(args);
        return fragment;
    }

    public QuestionAnswerFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mGameName = getArguments().getString(GAME_NAME);
            mChestText  = getArguments().getString(CHST_TXT);
            mQuestionTxt  = getArguments().getString(QTN_TXT);
            mAnswerText= getArguments().getString(ANS_TXT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_question_answer, container, false);
    }

    @Override
    public void onActivityCreated (Bundle savedInstanceState)
    {
        TextView quizTextLabel = (TextView)getView().findViewById(R.id.quizTextLbl);
        TextView chestTextLabel = (TextView)getView().findViewById(R.id.chest_description_lbl);
        quizTextLabel.setText(mQuestionTxt);
        chestTextLabel.setText(mChestText);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    public void clickSendButton(View v)
    {
        TextView answerTextLabel = (TextView)getView().findViewById(R.id.answerTxtView);
        String answerText = answerTextLabel.getText().toString();
        answerText = answerText.trim();
        if (answerText.equalsIgnoreCase(mAnswerText)) {
            //he wins!
        } else {
            //toast try again
            Toast.makeText(this.getActivity().getApplicationContext(),getText(R.string.wrong_answer),Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

}
