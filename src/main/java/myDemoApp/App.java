package myDemoApp;


import static spark.Spark.get;
import static spark.Spark.port;
import static spark.Spark.post;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import spark.ModelAndView;
import spark.template.mustache.MustacheTemplateEngine;


public class App {

    public static boolean findEvenOrOdd(ArrayList<Integer> array, int LBound,int HBound,String choice) {
        if (array == null) return false;
        if(LBound>=HBound)
          throw new IllegalArgumentException();

        int ch=0;
        if(choice.equals("odd"))
          ch=1;
        else if(!choice.equals("even"))
        throw new IllegalArgumentException();

        for(int x:array){
          if(ch==0&&x%2==0&&x>LBound&&x<HBound)
            return true;
          if(ch==1&&x%2!=0&&x>LBound&&x<HBound)
            return true;
        }
        return false;
      }
  

    public static void main(String[] args) {
        port(getHerokuAssignedPort());

        


        get("/", (req, res) -> "Hello, World");


        post("/compute", (req, res) -> {
  
            String input1 = req.queryParams("input1");
            java.util.Scanner sc1 = new java.util.Scanner(input1);
            sc1.useDelimiter("[;\r\n]+");
            java.util.ArrayList<Integer> inputList = new java.util.ArrayList<>();
            while (sc1.hasNext())
            {
              int value = Integer.parseInt(sc1.next().replaceAll("\\s",""));
              inputList.add(value);
            }
            sc1.close();
            System.out.println(inputList);
  
  
            String input2 = req.queryParams("input2").replaceAll("\\s","");
            int input2AsInt = Integer.parseInt(input2);

            String input3= req.queryParams("input3").replaceAll("\\s","");
            int input3AsInt=Integer.parseInt(input3);

            String input4= req.queryParams("input4").replaceAll("\\s","");
  
            boolean result = App.findEvenOrOdd(inputList, input2AsInt,input3AsInt,input4);
  
            Map<String, Boolean> map = new HashMap<String, Boolean>();
            map.put("result", result);
            return new ModelAndView(map, "compute.mustache");
          }, new MustacheTemplateEngine());

          get("/compute",
            (rq, rs) -> {
              Map<String, String> map = new HashMap<String, String>();
              map.put("result", "true if list has a chosen type of number between low bound and high bound.\n");
              map.put("exp", "1st box for list(one number per line) -- 2nd box for low bound -- 3rd box for high bound -- 4th boxfor choice (\"even\" or \"odd\")");
              return new ModelAndView(map, "compute.mustache");
            },
            new MustacheTemplateEngine());

  
    }


    static int getHerokuAssignedPort() {
        ProcessBuilder processBuilder = new ProcessBuilder();
        if (processBuilder.environment().get("PORT") != null) {
            return Integer.parseInt(processBuilder.environment().get("PORT"));
        }
        return 4567; //return default port if heroku-port isn't set (i.e. on localhost)
    }

}
