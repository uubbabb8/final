package test1225;



import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;

import org.jfree.chart.*;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.Series;
import org.jfree.data.time.Month;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;








 
public class test1225 extends JPanel
implements ActionListener{ 
	public JButton button = new JButton("Open file");
	public JButton button2 = new JButton("Apply");
	public JFileChooser chooser=new JFileChooser();
	public JTextField textfield=new JTextField();
	public ChartDisplayWidget myChart;
	public static Double[] data=new Double[60000];
	public static Double[] detect=new Double[60000];
	public JFrame demo = new JFrame();
	public Double textvalue;
	
	public test1225()
	{
		
        
		
        demo.setSize(1024, 576);
        
        button.setBounds(10,10,800,25);
        button.addActionListener(this);
        
        button2.setBounds(820,10,150,25);
        button2.addActionListener(this);
        
        
        textfield.setBounds(820,150,150,25);
        textfield.setText("0.0035");
        textvalue=Double.parseDouble(textfield.getText());
        
         
        JLabel label=new JLabel("Threshold");
        label.setBounds(820,125,150,25);
        
        myChart = new ChartDisplayWidget();
        myChart.setBounds(10,40,800,570);
        
        demo.setLayout(null);
        demo.add(myChart);
        demo.add(button);
        demo.add(button2);
        demo.add(textfield);
        demo.add(label);
        demo.setVisible(true);
        
        
	}


	public static class ChartDisplayWidget extends JPanel
	{
	    /**
	     * 
	     */
	    private static final long serialVersionUID = 1L;

	    private XYSeriesCollection chartData;
	    private JFreeChart chart;
	    private XYItemRenderer renderer;
	    public ChartDisplayWidget()
	    {
	        init();
	    }

	    public void init()
	    {
	        final XYDataset dataset = getSampleData();

	        chart = ChartFactory.createXYLineChart(
	        		"LineChart", "s", "V", dataset, PlotOrientation.VERTICAL, true, true, false);
	        renderer=chart.getXYPlot().getRenderer();
	        renderer.setBaseStroke(new BasicStroke(0.1f));
	        renderer.setSeriesPaint(0, new Color(0x50, 0x50, 0xff));
	        renderer.setSeriesPaint(1, new Color(0xff, 0x00, 0x00));
	        
	        final ChartPanel chartPanel = new ChartPanel(chart); 
	        chartPanel.setPreferredSize(getPreferredSize());
	        add(chartPanel);
	    }

	    @Override
	    public Dimension getPreferredSize() {
	        // given some values of w & h
	        return new Dimension(800, 450);
	    }

	    private XYDataset getSampleData()
	    {
	    	XYSeries s1 = new XYSeries("SKNA");
	    	XYSeries s2 = new XYSeries("detect");
	    	
	    	for(int i=1;i<=6000;i++)
	    	{
	    		
	    		s1.add(i,data[i]);
	    		
	    		s2.add(i,detect[i]);
	    	}
	    	
	    	chartData=new XYSeriesCollection();
	    	chartData.addSeries(s1);
	    	chartData.addSeries(s2);
	    	
	        return chartData;
	    }
	}
	
	
	
	public void actionPerformed(ActionEvent e) {
		
		String line = "";
	    String splitBy = ",";
	    XYSeriesCollection chartData;
	  
	    
	  
	    
        //Handle open button action.
        if (e.getSource() == button) {
        	
        	
        	
            int returnVal = chooser.showOpenDialog(test1225.this);
            
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                File file = chooser.getSelectedFile();
                BufferedReader br = null;
                try {
                br=new BufferedReader(new FileReader(file));
                XYSeries s1 = new XYSeries("SKNA");
                for(int k=1;k<=6000;k++){
                	
                	line = br.readLine();
                	
                	
    	            String[] test = line.split(splitBy);
    	            
    	            data[k]=Double.parseDouble(test[0]);
    	            System.out.println(data[k].toString());
    	            
    	            s1.add(k,data[k]);
    	        }   
                
    	    
                
            	
    	    	chartData=new XYSeriesCollection();
    	    	chartData.addSeries(s1);
    	    	
    	    	
    	        
                }catch(FileNotFoundException r){
        	        r.printStackTrace();
        	    }catch (IOException r){
        	        r.printStackTrace();
        	    }finally{
        	        if (br != null){
        	            try{
        	                br.close();
        	            } catch (IOException r){
        	                r.printStackTrace();
        	            }
        	        }
        	    }
                //This is where a real application would open the file.
                
            } else {
                
            }
           
        //Handle save button action.
            myChart = new ChartDisplayWidget();
            myChart.setBounds(10,40,800,600);
            demo.add(myChart);
	        demo.setVisible(true);
            
        }
        if (e.getSource() == button2) {
        	
        	
        	textvalue=Double.parseDouble(textfield.getText());
        	
                XYSeries s2 = new XYSeries("detect");
                	
                for(int k=1;k<=6000;k++){
                	
                	
                	if(data[k]>=textvalue) {
                		
    	            detect[k]=data[k]*1.25;
                	}else {
                		detect[k]=textvalue;
                	}
    	            s2.add(k,detect[k]);
    	            
    	        }   
                
    	    
                
            	
    	    	chartData=new XYSeriesCollection();
    	    	chartData.addSeries(s2);
    	    	
    	    	
    	        
                
                //This is where a real application would open the file.
                
        
           
        //Handle save button action.
            myChart = new ChartDisplayWidget();
            myChart.setBounds(10,40,800,600);
            demo.add(myChart);
	        demo.setVisible(true);
            
        }
    }
	
	
	
	 private static void createAndShowGUI() {
	        //Create and set up the window.
	        JFrame frame = new JFrame("FileChooser");
	        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	        //Add content to the window.
	        frame.add(new test1225());

	        //Display the window.
	        frame.pack();
	        
	    }
	
    public static void main(String[] args) { 
    	
    	  SwingUtilities.invokeLater(new Runnable() {
              public void run() {
                  //Turn off metal's use of bold fonts
                  
            	  createAndShowGUI();
              }
          });
    	
        
    }
}
