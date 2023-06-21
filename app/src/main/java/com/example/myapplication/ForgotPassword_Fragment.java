package com.example.myapplication;

import android.annotation.SuppressLint;
import android.content.res.ColorStateList;
import android.content.res.XmlResourceParser;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import java.util.Properties;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import java.util.Properties;

import javax.mail.Authenticator;
//import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class ForgotPassword_Fragment extends Fragment implements
        OnClickListener {
    private static View view;

    private static EditText emailId;
    private static TextView submit, back;

    public ForgotPassword_Fragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.forgotpassword_layout, container,
                false);
        initViews();
        setListeners();
        return view;
    }

    // Initialize the views
    private void initViews() {
        emailId = (EditText) view.findViewById(R.id.registered_emailid);
        submit = (TextView) view.findViewById(R.id.forgot_button);
        back = (TextView) view.findViewById(R.id.backToLoginBtn);

        // Setting text selector over textviews
        @SuppressLint("ResourceType") XmlResourceParser xrp = getResources().getXml(R.drawable.textview_selector);
        try {
            ColorStateList csl = ColorStateList.createFromXml(getResources(),
                    xrp);

            back.setTextColor(csl);
            submit.setTextColor(csl);

        } catch (Exception e) {
        }

    }

    // Set Listeners over buttons
    private void setListeners() {
        back.setOnClickListener(this);
        submit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.backToLoginBtn:

                // Replace Login Fragment on Back Presses
                new Registration().replaceLoginFragment();
                break;

            case R.id.forgot_button:

                // Call Submit button task
                submitButtonTask();
                break;

        }

    }

    private void submitButtonTask() {
        String getEmailId = emailId.getText().toString();

        // Pattern for email id validation
        Pattern p = Pattern.compile(Utils.regEx);

        // Match the pattern
        Matcher m = p.matcher(getEmailId);

        // First check if email id is not null else show error toast
//        if (getEmailId.equals("") || getEmailId.length() == 0||getEmailId.isEmpty())
//
//            new CustomToast().Show_Toast(getActivity(), view,
//                    "Please enter your Email Id.");
//
//            // Check if email id is valid or not
//        else if (!m.find())
//            new CustomToast().Show_Toast(getActivity(), view,
//                    "Your Email Id is Invalid.");
//
//            // Else submit email id and fetch passwod or do your stuff
//        else
//            Toast.makeText(getActivity(), "Get Forgot Password.",
//                    Toast.LENGTH_SHORT).show();

        // First check if the email ID is not empty
        if (getEmailId.isEmpty()) {
            new CustomToast().Show_Toast(getActivity(), view, "Please enter your Email ID.");
        }
        // Check if the email ID is valid
        else if (!m.matches()) {
            new CustomToast().Show_Toast(getActivity(), view, "Your Email ID is Invalid.");
        } else {
            // Execute the network operation in a separate thread using AsyncTask
            new SendVerificationCodeTask().execute(getEmailId);
        }
//        } else {
//            // Generate and send the verification code to the email ID
//            sendVerificationCode(getEmailId);
//        }
    }
    private class SendVerificationCodeTask extends AsyncTask<String, Void, Void> {
        @Override
        protected Void doInBackground(String... emailIds) {
            String emailId = emailIds[0];

            // Perform the network operation to send the verification code
            sendVerificationCode(emailId);

            return null;
        }
    }

    private void sendVerificationCode(String email) {
        // Generate a verification code (you can use any algorithm or method to generate a code)
        String verificationCode = generateVerificationCode();

        // Extract the domain from the email address
        String[] emailParts = email.split("@");
        if (emailParts.length != 2) {
            // Invalid email address format
            Toast.makeText(getActivity(), "Invalid email address.", Toast.LENGTH_SHORT).show();
            return;
        }
        String domain = emailParts[1];

        // Send the verification code to the email ID
        try {
            // Create a properties object and configure it with the email provider's settings based on the domain
            Properties props = new Properties();

            // Gmail SMTP settings
            if (domain.equals("gmail.com")) {
                props.put("mail.smtp.auth", "true");
                props.put("mail.smtp.starttls.enable", "true");
                props.put("mail.smtp.host", "smtp.gmail.com");
                props.put("mail.smtp.port", "587");
            }
            // Hotmail/Outlook SMTP settings
            else if (domain.equals("hotmail.com") || domain.equals("outlook.com")) {
                props.put("mail.smtp.auth", "true");
                props.put("mail.smtp.starttls.enable", "true");
                props.put("mail.smtp.host", "smtp.live.com");
                props.put("mail.smtp.port", "587");
            }
            // Yahoo SMTP settings
            else if (domain.equals("yahoo.com")) {
                props.put("mail.smtp.auth", "true");
                props.put("mail.smtp.starttls.enable", "true");
                props.put("mail.smtp.host", "smtp.mail.yahoo.com");
                props.put("mail.smtp.port", "587");
            }
            // SMTP settings for other email extensions
            else {
                // Replace the placeholders with the specific settings for your email provider
                props.put("mail.smtp.auth", "true");
                props.put("mail.smtp.starttls.enable", "true");
                props.put("mail.smtp.host", "your_smtp_host");
                props.put("mail.smtp.port", "your_smtp_port");
            }

            // Create a session with the properties and provide authentication information
            Session session = Session.getInstance(props, new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication("your_email", "your_password"); // Replace with your email and password
                }
            });



            // Create a message
           javax.mail.Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("your_email")); // Replace with the sender's email address
            message.setRecipients( javax.mail.Message.RecipientType.TO, InternetAddress.parse(email));
            message.setSubject("Verification Code");
            message.setText("Your verification code is: " + verificationCode);

//            try {
//                // Send the message
//                Transport.send(message);
//            } catch (MessagingException e) {
//                e.printStackTrace();
//            }
//
//            // Show a success message to the user
//            Toast.makeText(getActivity(), "Verification code sent to your email ID.", Toast.LENGTH_SHORT).show();

            try {
                // Send the message
                Transport.send(message);

                // Show a Toast message on the main thread
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getActivity(), "Verification code sent successfully", Toast.LENGTH_SHORT).show();
                    }
                });
            } catch (MessagingException e) {
                e.printStackTrace();

                // Show a Toast message on the main thread
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getActivity(), "Failed to send verification code", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        } catch (MessagingException e) {
            e.printStackTrace();
            // Handle any errors that occur during the email sending process
            Toast.makeText(getActivity(), "Failed to send verification code. Please try again later.", Toast.LENGTH_SHORT).show();
        }
    }

    private String generateVerificationCode() {
        // Generate a random verification code
        Random random = new Random();
        int code = 1000 + random.nextInt(9000); // Generate a 4-digit code
        return String.valueOf(code);
    }


}