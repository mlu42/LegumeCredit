package credit.calc.ipmc.legumecredit;

import android.widget.TextView;

/**
 * Created by mo on 8/5/14.
 */
public class ManureHelper {

    private TwoStateToggle manureType;
    private ThreeStateToggle incorpTime;
    private TwoStateToggle amount;

    private String manure;
    private String time;
    private String count;

    private TextView result;


    // url to make request
    private static String url = "file:///android_asset/ManureCredit.json";

    // JSON Node names
    private static final String TAG_MANURETYPE = "Solid";
    private static final String TAG_N = "N";
    private static final String TAG_P = "P";
    private static final String TAG_K = "K";
    private static final String TAG_S = "S";
    private static final String TAG_TIME_LONG = ">";
    private static final String TAG_TIME_TO = "-";
    private static final String TAG_TIME_SHORT = "<";


    // A table with all of the possible results that can be easily used to look up the result
    private int[][] resultTable = new int[10][6];

    {
        resultTable[0][0] = 150;
        resultTable[0][1] = 190;
        resultTable[0][2] = 100;
        resultTable[0][3] = 140;
        resultTable[1][0] = 120;
        resultTable[1][1] = 160;
        resultTable[1][2] = 70;
        resultTable[1][3] = 110;
        resultTable[2][0] = 90;
        resultTable[2][1] = 130;
        resultTable[2][2] = 40;
        resultTable[2][3] = 80;
    }

    /*
     * A constructor that takes all of the necessary layout objects to be read from or written to
     */
    public ManureHelper(TwoStateToggle mt, ThreeStateToggle it, TwoStateToggle am, TextView rs) {
        manureType = mt;
        incorpTime = it;
        amount = am;

        result = rs;


    }


    /*
     * Calculates the range by deciding which indexes to look up
     * and looking them up in the tables
     */
    public void calculate() {


        if (manureType.getCurrentState() == 0) {
            manure = "solid";
        } else {
            manure = "liquid";
        }

        switch (incorpTime.getCurrentState()) {
            case 0:
                time = ">";
                break;
            case 1:
                time = "-";
                break;
            case 2:
                time = "<";
                break;

        }
    // Creating JSON Parser instance
//        JSONParser jParser = new JSONParser();
//
//    // getting JSON string from URL
//        JSONObject json = jParser.getJSONFromUrl(url);
//
//        try {
//            // Getting Array of Contacts
//            //JSONArray manuretype = json.getJSONArray(TAG_MANURETYPE);
//
//
////        JSONObject jObject = new JSONObject(res);
////        JSONObject aJsonManure = jObject.getJSONObject(manure);
////
////        JSONObject aJasonAnimal= aJsonManure.getJSONObject("beef");
////        int aJasonrslt= aJasonAnimal.getInt("S");
//        //JSONObject jObject = new JSONObject();
//        JSONObject aJsonManure = json.getJSONObject(manure);
//
//        JSONObject aJasonAnimal= aJsonManure.getJSONObject("beef");
//        String aJasonrslt= aJasonAnimal.getString("S");
//
//
//
//        result.setText(aJasonrslt);


//            // looping through All Contacts
//            for(int i = 0; i < contacts.length(); i++){
//                JSONObject c = contacts.getJSONObject(i);
//
//                // Storing each json item in variable
//                String id = c.getString(TAG_ID);
//                String name = c.getString(TAG_NAME);
//                String email = c.getString(TAG_EMAIL);
//                String address = c.getString(TAG_ADDRESS);
//                String gender = c.getString(TAG_GENDER);
//
//                // Phone number is agin JSON Object
//                JSONObject phone = c.getJSONObject(TAG_PHONE);
//                String mobile = phone.getString(TAG_PHONE_MOBILE);
//                String home = phone.getString(TAG_PHONE_HOME);
//                String office = phone.getString(TAG_PHONE_OFFICE);
//

//        } catch (JSONException e) {
//            e.printStackTrace();
//        }









    }

}

//    try {
//        DefaultHttpClient httpclient = new DefaultHttpClient(new BasicHttpParams());
//        HttpPost httppost = new HttpPost("file:///android_asset/legume_help.html");
//        // Depends on your web service
//        //httppost.setHeader("Content-type", "application/json");
//
//        InputStream inputStream = null;
//        String res = null;
//        try {
//            HttpResponse response = httpclient.execute(httppost);
//            HttpEntity entity = response.getEntity();
//
//            inputStream = entity.getContent();
//            // json is UTF-8 by default
//            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"), 8);
//            StringBuilder sb = new StringBuilder();
//
//            String line = null;
//            while ((line = reader.readLine()) != null) {
//                sb.append(line + "\n");
//            }
//            res = sb.toString();
//        } catch (Exception e) {
//            // Oops
//        } finally {
//            try {
//                if (inputStream != null) inputStream.close();
//            } catch (Exception squish) {
//            }
//        }
//
//        //Log.v("TAG", res);
//
//        JSONObject jObject = new JSONObject(res);
//        JSONObject aJsonManure = jObject.getJSONObject(manure);
//
//        JSONObject aJasonAnimal= aJsonManure.getJSONObject("beef");
//        int aJasonrslt= aJasonAnimal.getInt("S");
//
//
//
//        result.setText(((Integer) aJasonrslt).toString());
//
//
//
//
//
//
//
//
//
//    } catch (JSONException e) {
//        e.printStackTrace();
//    }



//        if(species.getCurrentState()> -1 && soil.getCurrentState()> -1 && standDensity.getCurrentState()> -1 && regrowth.getCurrentState()> -1) {
//            r = standDensity.getCurrentState();
//
//            if (soil.getCurrentState() == 0) {
//                c =regrowth.getCurrentState();
//            } else  {
//                if(regrowth.getCurrentState() == 0){
//                    c=2;
//                }
//                else{
//                    c
//                            =3;
//                }
//            }
//
//
//            if (species.getCurrentState() == 0) {
//                result.setText(((Integer)resultTable[r][c]).toString());
//            } else {
//                temp = (int) Math.round(resultTable[r][c] * 0.8);
//                result.setText(((Integer)temp).toString());
//            }
//       }






//    public List readJsonStream(InputStream in) throws IOException {
//        JsonReader reader = new JsonReader(new InputStreamReader(in, "UTF-8"));
//        try {
//            return readMessagesArray(reader);
//            finally{
//                reader.close();
//            }
//        }
//
//    public List readMessagesArray(JsonReader reader) throws IOException {
//        List messages = new ArrayList();
//
//        reader.beginArray();
//        while (reader.hasNext()) {
//            messages.add(readMessage(reader));
//        }
//        reader.endArray();
//        return messages;
//    }
//
//    public Message readMessage(JsonReader reader) throws IOException {
//        long id = -1;
//        String text = null;
//        //User user = null;
//        List geo = null;
//
//        reader.beginObject();
//        while (reader.hasNext()) {
//            String name = reader.nextName();
//            if (name.equals("id")) {
//                id = reader.nextLong();
//            } else if (name.equals("text")) {
//                text = reader.nextString();
//            } else if (name.equals("geo")) {
//                geo = readDoublesArray(reader);
//
//            } else {
//                reader.skipValue();
//            }
//        }
//        reader.endObject();
//        return new Message(id, text,geo);
//    }
//
//    public List readDoublesArray(JsonReader reader) throws IOException {
//        List doubles = new ArrayList();
//
//        reader.beginArray();
//        while (reader.hasNext()) {
//            doubles.add(reader.nextDouble());
//        }
//        reader.endArray();
//        return doubles;
//    }
//
//    }







