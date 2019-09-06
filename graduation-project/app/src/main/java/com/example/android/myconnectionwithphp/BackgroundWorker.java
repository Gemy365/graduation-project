package com.example.android.myconnectionwithphp;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

class BackgroundWorker extends AsyncTask<String, Void, String> {
    Context context;
//    private AlertDialog alertDialog;

    BackgroundWorker(Context ctx) {
        context = ctx;
    }

    public BackgroundWorker() {

    }

    @Override
    protected String doInBackground(String... params) {
        String type = params[0];
        String doctor_url = "http://teriaq-project.000webhostapp.com/login_signup_doctor.php";
        String patient_url = "http://teriaq-project.000webhostapp.com/login_signup_patient.php";
        String pharmacist_url = "http://teriaq-project.000webhostapp.com/login_signup_pharmacies.php";
        String json_url = "http://teriaq-project.000webhostapp.com/json.php";
        String prescription_url = "http://teriaq-project.000webhostapp.com/prescription.php";

        // Login Doctor.
        if (type.equals("login_doc")) {
            try {
                String username = params[1];
                String password = params[2];

                URL url = new URL(doctor_url);

                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoInput(true);
                httpURLConnection.setDoOutput(true);

                OutputStream outputStream = httpURLConnection.getOutputStream();

                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));

                String post_data = URLEncoder.encode("username", "UTF-8") + "=" + URLEncoder.encode(username, "UTF-8")
                        + "&" + URLEncoder.encode("password", "UTF-8") + "=" + URLEncoder.encode(password, "UTF-8");
                bufferedWriter.write(post_data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();

                InputStream inputStream = httpURLConnection.getInputStream();

                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));

                String result = "";
                String line = "";

                while ((line = bufferedReader.readLine()) != null) {
                    result += line;
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return result;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        // Login Patient.
        else if (type.equals("login_patient")) {
            try {
                String username = params[1];
                String password = params[2];

                URL url = new URL(patient_url);

                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoInput(true);
                httpURLConnection.setDoOutput(true);

                OutputStream outputStream = httpURLConnection.getOutputStream();

                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));

                String post_data = URLEncoder.encode("login_patient", "UTF-8") + "=" + URLEncoder.encode(type, "UTF-8")
                        + "&" + URLEncoder.encode("username", "UTF-8") + "=" + URLEncoder.encode(username, "UTF-8")
                        + "&" + URLEncoder.encode("password", "UTF-8") + "=" + URLEncoder.encode(password, "UTF-8");
                bufferedWriter.write(post_data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();

                InputStream inputStream = httpURLConnection.getInputStream();

                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));

                String result = "";
                String line = "";

                while ((line = bufferedReader.readLine()) != null) {
                    result += line;
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return result;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        // SignUp Patient.
        else if (type.equals("signup_patient")) {
            try {
                String username = params[1];
                String password = params[2];
                String mPassword = params[3];
                String email = params[4];
                String phone = params[5];
                String address = params[6];
                String fullName = params[7];

                URL url = new URL(patient_url);

                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoInput(true);
                httpURLConnection.setDoOutput(true);

                OutputStream outputStream = httpURLConnection.getOutputStream();

                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));

                String post_data = URLEncoder.encode("user", "UTF-8") + "=" + URLEncoder.encode(username, "UTF-8")
                        + "&" + URLEncoder.encode("pass", "UTF-8") + "=" + URLEncoder.encode(password, "UTF-8")
                        + "&" + URLEncoder.encode("match_pass", "UTF-8") + "=" + URLEncoder.encode(mPassword, "UTF-8")
                        + "&" + URLEncoder.encode("email", "UTF-8") + "=" + URLEncoder.encode(email, "UTF-8")
                        + "&" + URLEncoder.encode("phone", "UTF-8") + "=" + URLEncoder.encode(phone, "UTF-8")
                        + "&" + URLEncoder.encode("address", "UTF-8") + "=" + URLEncoder.encode(address, "UTF-8")
                        + "&" + URLEncoder.encode("fullName", "UTF-8") + "=" + URLEncoder.encode(fullName, "UTF-8");

                bufferedWriter.write(post_data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();

                InputStream inputStream = httpURLConnection.getInputStream();

                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));

                String result = "";
                String line = "";

                while ((line = bufferedReader.readLine()) != null) {
                    result += line;
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return result;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        // Login Pharmacist.
        else if (type.equals("login_pharmacist")) {
            try {
                String username = params[1];
                String password = params[2];

                URL url = new URL(pharmacist_url);

                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoInput(true);
                httpURLConnection.setDoOutput(true);

                OutputStream outputStream = httpURLConnection.getOutputStream();

                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));

                String post_data = URLEncoder.encode("username", "UTF-8") + "=" + URLEncoder.encode(username, "UTF-8")
                        + "&" + URLEncoder.encode("password", "UTF-8") + "=" + URLEncoder.encode(password, "UTF-8");
                bufferedWriter.write(post_data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();

                InputStream inputStream = httpURLConnection.getInputStream();

                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));

                String result = "";
                String line = "";

                while ((line = bufferedReader.readLine()) != null) {
                    result += line;
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return result;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        // Doc Json.
        else if (type.equals("doc_data")) {
            try {
                String usernameDoc = params[1];

                URL url = new URL(json_url);

                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoInput(true);
                httpURLConnection.setDoOutput(true);

                OutputStream outputStream = httpURLConnection.getOutputStream();

                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));

                String post_data = URLEncoder.encode("doc_data", "UTF-8") + "=" + URLEncoder.encode(type, "UTF-8")
                        + "&" + URLEncoder.encode("usernamedoc", "UTF-8") + "=" + URLEncoder.encode(usernameDoc, "UTF-8");
                bufferedWriter.write(post_data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();

                InputStream inputStream = httpURLConnection.getInputStream();

                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));

                String result = "";
                String line = "";

                while ((line = bufferedReader.readLine()) != null) {
                    result += line;
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return result;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        // Edit Data Doc.
        else if (type.equals("edit_doctor")) {
            try {
                String idDoc = params[1];
                String usernameDoc = params[2];
                String oldPasswordDoc = params[3];
                String newPasswordDoc = params[4];
                String emailDoc = params[5];
                String phoneDoc = params[6];
                String addressDoc = params[7];

                URL url = new URL(json_url);

                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoInput(true);
                httpURLConnection.setDoOutput(true);

                OutputStream outputStream = httpURLConnection.getOutputStream();

                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));

                String post_data = URLEncoder.encode("edit_doctor", "UTF-8") + "=" + URLEncoder.encode(type, "UTF-8")
                        + "&" + URLEncoder.encode("id_doc", "UTF-8") + "=" + URLEncoder.encode(idDoc, "UTF-8")
                        + "&" + URLEncoder.encode("username_doc", "UTF-8") + "=" + URLEncoder.encode(usernameDoc, "UTF-8")
                        + "&" + URLEncoder.encode("oldpassword_doc", "UTF-8") + "=" + URLEncoder.encode(oldPasswordDoc, "UTF-8")
                        + "&" + URLEncoder.encode("newpassword_doc", "UTF-8") + "=" + URLEncoder.encode(newPasswordDoc, "UTF-8")
                        + "&" + URLEncoder.encode("email_doc", "UTF-8") + "=" + URLEncoder.encode(emailDoc, "UTF-8")
                        + "&" + URLEncoder.encode("phone_doc", "UTF-8") + "=" + URLEncoder.encode(phoneDoc, "UTF-8")
                        + "&" + URLEncoder.encode("Address_doc", "UTF-8") + "=" + URLEncoder.encode(addressDoc, "UTF-8");
                bufferedWriter.write(post_data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();

                InputStream inputStream = httpURLConnection.getInputStream();

                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));

                String result = "";
                String line = "";

                while ((line = bufferedReader.readLine()) != null) {
                    result += line;
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return result;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }        // Edit Data Patient.
        else if (type.equals("edit_patient")) {
            try {
                String idPatient = params[1];
                String usernameatient = params[2];
                String oldPasswordatient = params[3];
                String newPasswordatient = params[4];
                String emailatient = params[5];
                String phoneatient = params[6];
                String addressatient = params[7];

                URL url = new URL(json_url);

                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoInput(true);
                httpURLConnection.setDoOutput(true);

                OutputStream outputStream = httpURLConnection.getOutputStream();

                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));

                String post_data = URLEncoder.encode("edit_patient", "UTF-8") + "=" + URLEncoder.encode(type, "UTF-8")
                        + "&" + URLEncoder.encode("id_patient", "UTF-8") + "=" + URLEncoder.encode(idPatient, "UTF-8")
                        + "&" + URLEncoder.encode("username_patient", "UTF-8") + "=" + URLEncoder.encode(usernameatient, "UTF-8")
                        + "&" + URLEncoder.encode("oldpassword_patient", "UTF-8") + "=" + URLEncoder.encode(oldPasswordatient, "UTF-8")
                        + "&" + URLEncoder.encode("newpassword_patient", "UTF-8") + "=" + URLEncoder.encode(newPasswordatient, "UTF-8")
                        + "&" + URLEncoder.encode("email_patient", "UTF-8") + "=" + URLEncoder.encode(emailatient, "UTF-8")
                        + "&" + URLEncoder.encode("phone_patient", "UTF-8") + "=" + URLEncoder.encode(phoneatient, "UTF-8")
                        + "&" + URLEncoder.encode("Address_patient", "UTF-8") + "=" + URLEncoder.encode(addressatient, "UTF-8");
                bufferedWriter.write(post_data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();

                InputStream inputStream = httpURLConnection.getInputStream();

                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));

                String result = "";
                String line = "";

                while ((line = bufferedReader.readLine()) != null) {
                    result += line;
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return result;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        // Pharmacy Json.
        else if (type.equals("pharmacy_data")) {
            try {
                String usernamePharmacy = params[1];

                URL url = new URL(json_url);

                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoInput(true);
                httpURLConnection.setDoOutput(true);

                OutputStream outputStream = httpURLConnection.getOutputStream();

                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));

                String post_data = URLEncoder.encode("pharmacy_data", "UTF-8") + "=" + URLEncoder.encode(type, "UTF-8")
                        + "&" + URLEncoder.encode("pharmacy_name", "UTF-8") + "=" + URLEncoder.encode(usernamePharmacy, "UTF-8");
                bufferedWriter.write(post_data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();

                InputStream inputStream = httpURLConnection.getInputStream();

                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));

                String result = "";
                String line = "";

                while ((line = bufferedReader.readLine()) != null) {
                    result += line;
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return result;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        // Send Prescription.
        else if (type.equals("write_prescription")) {
            try {
                String DocID = params[1];
                String MedicineName1 = (params[2] != null) ? params[2] : "";
                String MedicineName2 = (params[3] != null) ? params[3] : "";
                String MedicineName3 = (params[4] != null) ? params[4] : "";
                String MedicineName4 = (params[5] != null) ? params[5] : "";
                String MedicineName5 = (params[6] != null) ? params[6] : "";
                String Cap1 = (params[7] != null) ? params[7] : "";
                String Cap2 = (params[8] != null) ? params[8] : "";
                String Cap3 = (params[9] != null) ? params[9] : "";
                String Cap4 = (params[10] != null) ? params[10] : "";
                String Cap5 = (params[11] != null) ? params[11] : "";
                String Day1 = (params[12] != null) ? params[12] : "";
                String Day2 = (params[13] != null) ? params[13] : "";
                String Day3 = (params[14] != null) ? params[14] : "";
                String Day4 = (params[15] != null) ? params[15] : "";
                String Day5 = (params[16] != null) ? params[16] : "";
                String PatientName = (params[17] != null) ? params[17] : "";

                URL url = new URL(json_url);

                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoInput(true);
                httpURLConnection.setDoOutput(true);

                OutputStream outputStream = httpURLConnection.getOutputStream();

                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));

                String post_data = URLEncoder.encode("write_prescription", "UTF-8") + "=" + URLEncoder.encode(type, "UTF-8")
                        + "&" + URLEncoder.encode("docID", "UTF-8") + "=" + URLEncoder.encode(DocID, "UTF-8")
                        + "&" + URLEncoder.encode("sendMedicine1", "UTF-8") + "=" + URLEncoder.encode(MedicineName1, "UTF-8")
                        + "&" + URLEncoder.encode("sendMedicine2", "UTF-8") + "=" + URLEncoder.encode(MedicineName2, "UTF-8")
                        + "&" + URLEncoder.encode("sendMedicine3", "UTF-8") + "=" + URLEncoder.encode(MedicineName3, "UTF-8")
                        + "&" + URLEncoder.encode("sendMedicine4", "UTF-8") + "=" + URLEncoder.encode(MedicineName4, "UTF-8")
                        + "&" + URLEncoder.encode("sendMedicine5", "UTF-8") + "=" + URLEncoder.encode(MedicineName5, "UTF-8")
                        + "&" + URLEncoder.encode("cam_mara1", "UTF-8") + "=" + URLEncoder.encode(Cap1, "UTF-8")
                        + "&" + URLEncoder.encode("cam_mara2", "UTF-8") + "=" + URLEncoder.encode(Cap2, "UTF-8")
                        + "&" + URLEncoder.encode("cam_mara3", "UTF-8") + "=" + URLEncoder.encode(Cap3, "UTF-8")
                        + "&" + URLEncoder.encode("cam_mara4", "UTF-8") + "=" + URLEncoder.encode(Cap4, "UTF-8")
                        + "&" + URLEncoder.encode("cam_mara5", "UTF-8") + "=" + URLEncoder.encode(Cap5, "UTF-8")
                        + "&" + URLEncoder.encode("cam_uom1", "UTF-8")  + "=" + URLEncoder.encode(Day1, "UTF-8")
                        + "&" + URLEncoder.encode("cam_uom2", "UTF-8")  + "=" + URLEncoder.encode(Day2, "UTF-8")
                        + "&" + URLEncoder.encode("cam_uom3", "UTF-8")  + "=" + URLEncoder.encode(Day3, "UTF-8")
                        + "&" + URLEncoder.encode("cam_uom4", "UTF-8")  + "=" + URLEncoder.encode(Day4, "UTF-8")
                        + "&" + URLEncoder.encode("cam_uom5", "UTF-8")  + "=" + URLEncoder.encode(Day5, "UTF-8")
                        + "&" + URLEncoder.encode("sendPatientName", "UTF-8") + "=" + URLEncoder.encode(PatientName, "UTF-8");
                bufferedWriter.write(post_data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();

                InputStream inputStream = httpURLConnection.getInputStream();

                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));

                String result = "";
                String line = "";

                while ((line = bufferedReader.readLine()) != null) {
                    result += line;
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return result;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        // Patient Data.
        else if (type.equals("patient_data")) {
            try {
                String usernamePatient = params[1];

                URL url = new URL(json_url);

                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoInput(true);
                httpURLConnection.setDoOutput(true);

                OutputStream outputStream = httpURLConnection.getOutputStream();

                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));

                String post_data = URLEncoder.encode("patient_data", "UTF-8") + "=" + URLEncoder.encode(type, "UTF-8")
                        + "&" + URLEncoder.encode("username_patient", "UTF-8") + "=" + URLEncoder.encode(usernamePatient, "UTF-8");
                bufferedWriter.write(post_data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();

                InputStream inputStream = httpURLConnection.getInputStream();

                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));

                String result = "";
                String line = "";

                while ((line = bufferedReader.readLine()) != null) {
                    result += line;
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return result;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        // Patient Data.
        else if (type.equals("determin_pharmacy")) {
            try {
                String PrescriptionID = (params[1] != null) ? params[1] : "";
                String DrugID = (params[2] != null) ? params[2] : "";
                String PharmacyID = (params[3] != null) ? params[3] : "";

                URL url = new URL(json_url);

                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoInput(true);
                httpURLConnection.setDoOutput(true);

                OutputStream outputStream = httpURLConnection.getOutputStream();

                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));

                String post_data = URLEncoder.encode("determin_pharmacy", "UTF-8") + "=" + URLEncoder.encode(type, "UTF-8")
                        + "&" + URLEncoder.encode("prescription_id", "UTF-8") + "=" + URLEncoder.encode(PrescriptionID, "UTF-8")
                        + "&" + URLEncoder.encode("drug_phar_id", "UTF-8") + "=" + URLEncoder.encode(DrugID, "UTF-8")
                        + "&" + URLEncoder.encode("pharmacy_id", "UTF-8") + "=" + URLEncoder.encode(PharmacyID, "UTF-8");
                bufferedWriter.write(post_data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();

                InputStream inputStream = httpURLConnection.getInputStream();

                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));

                String result = "";
                String line = "";

                while ((line = bufferedReader.readLine()) != null) {
                    result += line;
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return result;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }    // Patient Data.
        else if (type.equals("update_quantity")) {
            try {
                String PharmacyID = params[1];
                String DrugWorldID = params[2];
                String AddQuantity = params[3];

                URL url = new URL(json_url);

                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoInput(true);
                httpURLConnection.setDoOutput(true);

                OutputStream outputStream = httpURLConnection.getOutputStream();

                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));

                String post_data = URLEncoder.encode("update_quantity", "UTF-8") + "=" + URLEncoder.encode(type, "UTF-8")
                        + "&" + URLEncoder.encode("id_pharmacy", "UTF-8") + "=" + URLEncoder.encode(PharmacyID, "UTF-8")
                        + "&" + URLEncoder.encode("drugpharmacy", "UTF-8") + "=" + URLEncoder.encode(DrugWorldID, "UTF-8")
                        + "&" + URLEncoder.encode("quantity", "UTF-8") + "=" + URLEncoder.encode(AddQuantity, "UTF-8");
                bufferedWriter.write(post_data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();

                InputStream inputStream = httpURLConnection.getInputStream();

                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));

                String result = "";
                String line = "";

                while ((line = bufferedReader.readLine()) != null) {
                    result += line;
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return result;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else if (type.equals("close")) {
            try {
                String PharmacyIDClose = params[1];
                String PreIDClose = params[2];
                String DrugIDClose = params[3];
                String QuantityClose = params[4];

                URL url = new URL(json_url);

                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoInput(true);
                httpURLConnection.setDoOutput(true);

                OutputStream outputStream = httpURLConnection.getOutputStream();

                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));

                String post_data = URLEncoder.encode("close", "UTF-8") + "=" + URLEncoder.encode(type, "UTF-8")
                        + "&" + URLEncoder.encode("pharmacy_ID_close", "UTF-8") + "=" + URLEncoder.encode(PharmacyIDClose, "UTF-8")
                        + "&" + URLEncoder.encode("prescripyion_close", "UTF-8") + "=" + URLEncoder.encode(PreIDClose, "UTF-8")
                        + "&" + URLEncoder.encode("drug_id_close", "UTF-8") + "=" + URLEncoder.encode(DrugIDClose, "UTF-8")
                        + "&" + URLEncoder.encode("quantaty_close", "UTF-8") + "=" + URLEncoder.encode(QuantityClose, "UTF-8");
                bufferedWriter.write(post_data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();

                InputStream inputStream = httpURLConnection.getInputStream();

                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));

                String result = "";
                String line = "";

                while ((line = bufferedReader.readLine()) != null) {
                    result += line;
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return result;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    protected void onPreExecute() {
        //    Toast.makeText(context, "Welcome Gemy", Toast.LENGTH_SHORT).show();
//        alertDialog = new AlertDialog.Builder(context).create();
//        alertDialog.setTitle("Alert!!");
    }

    // Response of Server.
    @Override
    protected void onPostExecute(String result) {
        // Remove All HTML From Response.
//        result = result.replaceAll("\\<.*?>(.*?)\\<.*?>","");  //Removes all items between brackets
//        result = result.replaceAll("<(.*?)\\>","");  //Removes all items in brackets
//        result = result.replaceAll("<(.*?)\\\n",""); //Must be underneath
//        result = result.replaceFirst("(.*?)\\>", "");    //Removes any connected item to the last bracket
//        result = result.replaceAll("&nbsp;","");        // Remove any space.
//        result = result.replaceAll("&amp;","");         // Remove any & char.

//        alertDialog.setMessage(result);
//        alertDialog.show();

    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }
}


// IMPORTANT..Will Use It To GET Data Like [Query].
//InputStream inputStream = null;
//    String line = null, result = null, data[];
//
//    private void get_data(){
//        try {
//            url url = new url(adress);
//            HttpURLConnection httpsURLConnection = (HttpURLConnection) url.openConnection();
//            httpsURLConnection.setRequestMethod("GET");
//
//            inputStream = new BufferedInputStream(httpsURLConnection.getInputStream());
//
//        } catch (MalformedURLException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        // read input stream into a string
//        try{
//            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
//            StringBuilder stringBuilder = new StringBuilder();
//
//            while ((line = bufferedReader.readLine()) != null){
//                stringBuilder.append(line + "\n");
//            }
//
//            inputStream.close();
//            result = stringBuilder.toString();
//        }
//        catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        // Parse json data
//        try{
//            JSONArray jsonArray = new JSONArray(result);
//            JSONObject jsonObject = null;
//
//            data = new String[jsonArray.length()];
//            for (int i=0; i<jsonArray.length(); i++){
//                jsonObject = jsonArray.getJSONObject(i);
//                data[i] = jsonObject.getString("user_adress"); // column name
//            }
//        }
//        catch (Exception e){
//            e.printStackTrace();
//        }
//
//        result = result.replaceAll("<(.*?)\\>"," ");  //Removes all items in brackets
//                result = result.replaceAll("<(.*?)\\\n"," "); //Must be undeneath
//                result = result.replaceFirst("(.*?)\\>", " ");    //Removes any connected item to the last bracket
//                result = result.replaceAll("&nbsp;"," ");
//                result = result.replaceAll("&amp;"," ");
//    }
