/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eleanalysis;


import com.aquafx_project.AquaFx;
import javafx.scene.shape.Rectangle;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.MultipleSelectionModel;
import javafx.scene.control.RadioButton;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 *
 * @author Yan
 *   
 *   leftVBox centerbox  rightVBox
 *   ___________________________
 *   |sample   |     |         |
 *   |infoVBox |     |matchList|
 *   |_________|     | VBox    |
 *   |         |     |_________|
 *   |   Table |     |         |
 *   |         |     |         |
 *   |         |     |  Match  |
 *   |         |     |Selection|
 *   |_________|     |   Box   |
 *   |  table  |     |         |
 *   | controls|     |         |
 *   |_________|     |         |
 *   |ele input|     |         |
 *   |_________|_____|_________|
 */


public class EleAnalysis extends Application {
   // private RadioButton weightPercentRadio, atomicPercentRadio; // for selecting data input type
    //private Label elementAdd, concAdd, srcFileName; 
    private Stage stage, preferencesStage;
   // private SampleLibrary samLibrary;
    //private SampleLibrary multiOpen;
    //private TableView<Element> table;
   // private TableView<Match> matchList;
   // private TableView<Element> matchSelection;
    //private ListView<String> libraryLV;
   // private ListView<String> searchLV;
   // private  final ObservableList<Element> eleTable = FXCollections.observableArrayList();
    //private  final ObservableList<Match> matchTable = FXCollections.observableArrayList(); // List of matches
    //private  final ObservableList<String> libraryTable = FXCollections.observableArrayList();
    //private  final ObservableList<String> searchLibrary = FXCollections.observableArrayList(); 
    private  int eleTableIndex;
    SampleLibrary cachedLib;
    //private TextField  prefLibraryTextField;
    //private TableColumn concWt, concAt;
    //public Stage stage = new Stage();
    @Override
    public void init(){
//        AquaFx.style();
    }
    @Override
    public void start(Stage stage) throws Exception {
        
        
        /*FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLDocument.fxml"));
        Parent root = loader.load();
        
        FXMLDocumentController controller = (FXMLDocumentController) loader.getController();
        controller.init(stage);
        */
        
        SampleLibrary cachedLib = new SampleLibrary(); // used to hold data read in from files
        final ObservableList<Element> eleTable = FXCollections.observableArrayList(); // list that links to Sample List
        final ObservableList<Match> matchTable = FXCollections.observableArrayList(); // list that links to Match List
        final ObservableList<String> libraryTable = FXCollections.observableArrayList(); // links to Library ListView under preferences
        final ObservableList<String> searchLibrary = FXCollections.observableArrayList(); // links to selected search library ListView under preferences
        final RadioButton weightPercentRadio = new RadioButton("Wt%"); // Button for selecting what type of data is being input(Weight %)
        final RadioButton  atomicPercentRadio = new RadioButton("At%"); // Button for selecting what type of data is being input(Atomic %)
        Button calcButton = new Button(weightPercentRadio.isSelected()? "Calc At%": "Calc Wt%"); // Button for calculating missing values
        TableView<Element> elementTable = new TableView<>(); // shows Sample/input data. Links to ObservableList eleTable
        TableView<Match> matchList = new TableView<>(); //shows sample data. Links to ObservableList matchTable
        TableView<Element> matchSelection = new TableView<>(); // shows the item that is selected from matchList
        //This is used to determine where to Save to Library
        Label prefLibraryLabel = new Label("Enter library name:     ");
        TextField prefLibraryTextField = new TextField("main");
        // File Chooser for opening spreadsheets
        FileChooser fileChooserLib = new FileChooser();
        /*fileChooserSpread = new FileChooser();
        fileChooserSpread.setInitialDirectory(new File(System.getProperty("user.home"))); 
        fileChooserSpread.setTitle("Open Resource File");
        fileChooserSpread.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("XLS", "*.xls"),
                new FileChooser.ExtensionFilter("XLSX", "*.xlsx")
        );  */  
        //File Chooser for Opening library
        //libDirectory = new File("C:\\Users\\Yan\\Documents\\NetBeansProjects\\EleAnalysis\\library");
        fileChooserLib.setTitle("Open a Library");
        fileChooserLib.setInitialDirectory(new File("C:\\Users\\Yan\\Documents\\NetBeansProjects\\EleAnalysis\\library"));
        fileChooserLib.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("DAT", "*.dat"));
       //BorderPane that holds an VBox in the left position. This VBox holds the table and a HBox(hb2).
       // The hb1 is an HBox in the bottom position of BorderPane
        // hb3 holds the buttons that control the eleTable
        BorderPane root = new BorderPane();
        Scene scene = new Scene(root, 800, 600);
        stage.setScene(scene);
        stage.setResizable(true);
        stage.setTitle("WDXRF Elemental Analysis");
        VBox leftVBox = new VBox(); 
        leftVBox.setPadding(new Insets(5));
        

        // Data Table
        TableColumn concWt = new TableColumn("Weight %"); 
        TableColumn concAt = new TableColumn("Atomic %");

        elementTable.setEditable(true);     
        TableColumn elementCol = new TableColumn("Element");
        elementCol.setPrefWidth(100);
        elementCol.setMaxWidth(100);
        elementCol.setCellValueFactory(
                new PropertyValueFactory<>("elementName"));
        concWt.setPrefWidth(100);
        concWt.setMaxWidth(100);
        concWt.setCellValueFactory(
                new PropertyValueFactory<>("concWeight"));
        concAt.setPrefWidth(100);
        concAt.setMaxWidth(100);
        concAt.setCellValueFactory(
                new PropertyValueFactory<>("atomicWeight"));
        elementTable.getColumns().addAll(elementCol,concWt,concAt);
        elementTable.setMaxWidth(300);
        elementTable.setItems(eleTable); 
        //Initial Settings
       concWt.setEditable(true);
       concAt.setEditable(false);
       
        
        
        
        /*
            Sample information area. Includes the name of the sample,
       */
        VBox sampleInfoVBox = new VBox();
        sampleInfoVBox.setPadding(new Insets(5));
        sampleInfoVBox.setAlignment(Pos.CENTER);
        HBox sampleInfoHBox = new HBox(); // Holds Sample Name/Add Library Button
        sampleInfoHBox.setPadding(new Insets(5));
        TextField samName = new TextField();
        samName.setPromptText("Enter Name");
        samName.setPrefWidth(50);
        samName.setPrefWidth(150);
        Button addToLibraryButton = new Button("Add to Library");
        addToLibraryButton.setPrefSize(125, 25);
        addToLibraryButton.setOnAction((event)->{
                if((new Sample(eleTable, "temp" ,weightPercentRadio.isSelected())).isComplete())
                    tableViewSave(eleTable, eleTableIndex, prefLibraryTextField.getText(),samName.getText(), weightPercentRadio);
                else
                    Alert.loadMessage("Your total concentration is not between 80-100");
         
        });
        
        HBox inputDataType = new HBox();
        inputDataType.setPadding(new Insets(10,5,5,10));
        Label inputDataTypeLabel = new Label("Data type:      ");
        ToggleGroup elementDataType = new ToggleGroup();
        //weightPercentRadio = new RadioButton("Wt%");
        //Initial Settings for when the program boots
        weightPercentRadio.setSelected(true);
        //atomicPercentRadio = new RadioButton("Atomic%");
        weightPercentRadio.setToggleGroup(elementDataType);
        atomicPercentRadio.setToggleGroup(elementDataType);
       
        weightPercentRadio.setOnAction((event)->{
            calcButton.setText("Calc At%");
            concWt.setEditable(true);
            concAt.setEditable(false);
                //new PropertyValueFactory<>("concWeight"));
        });
        atomicPercentRadio.setOnAction((event)->{
            calcButton.setText("Calc Wt%");
             concWt.setEditable(false);
            concAt.setEditable(true);
        });
        inputDataType.getChildren().addAll(inputDataTypeLabel, weightPercentRadio,atomicPercentRadio);
        Label srcFileName = new Label("No file selected");
        sampleInfoHBox.getChildren().addAll(samName,addToLibraryButton);
        sampleInfoVBox.getChildren().addAll(sampleInfoHBox, inputDataType, srcFileName);
      
         
        
        
        
        
        
        
        
        
        
        
       
       
       
       
       
       
       
       
       
       
       
       HBox tableControlsHBox = new HBox();
       tableControlsHBox.setPadding(new Insets(5,0,0,0));
       //Table Index Buttons
        Button downIndex = new Button("<-");
        Button upIndex = new Button("->");
        Button removeEl = new Button ("Delete");
        
        tableControlsHBox.setAlignment(Pos.CENTER);
        
        downIndex.setOnAction((event)->{
            if(cachedLib.getLength()==1){} // button does nothing if there is only one item in array
            else{
                if (eleTableIndex==0){ // loops back to the end of the array
                    eleTableIndex = cachedLib.getLength();
                }
                updateElementTable(eleTable, (eleTableIndex-1), srcFileName);
            }
        });
        upIndex.setOnAction((event)->{
            if(cachedLib.getLength()==1){} // button does nothing if there is only one item in array
            else{
                if(eleTableIndex == (cachedLib.getLength()-1)) // loops back to the beginning of the array
                    eleTableIndex = 0;
                updateElementTable(eleTable, (eleTableIndex+1), srcFileName);
            }
            
        });
       removeEl.setOnAction((event)->{
            if(elementTable.getSelectionModel().getSelectedItem()!= null){
                eleTable.remove(elementTable.getSelectionModel().getSelectedIndex());
            }
          
        });
       
       //Calculates the value not entered(Atomic percent or weight percent)
       calcButton.setOnAction((event)->{
           
          Sample temp = new Sample(eleTable,samName.getText(),weightPercentRadio.isSelected());
          eleTable.clear();
          eleTable.addAll(FXCollections.observableArrayList(temp.getArrayCopy()));
         
       });
       tableControlsHBox.getChildren().addAll(downIndex,upIndex,removeEl,calcButton);
        
       
       
       
       
       
       HBox eleInputHBox = new HBox();
        eleInputHBox.setPadding(new Insets(2,0,2,0));
        // for Table Input
        Label elementsLabel = new Label("Element");
        elementsLabel.setPadding(new Insets(3));
        Label concLabel = new Label("Weight %");
        concLabel.setPadding(new Insets(3));
        TextField elementsText = new TextField();
        elementsText.setPromptText("Element");
        TextField concText = new TextField();
        elementsText.setMaxWidth(70);
        concText.setMaxWidth(40);
        
         // Adds data to eleTable and clears text fields
        Button add = new Button("Add");
        add.setOnAction((ActionEvent event)->{
            double concInput;
            try{
                concInput = Double.parseDouble(concText.getText());
            }catch(NumberFormatException e){
                //new Alert("Not a number");
                Alert.loadMessage("Not a number");
                return;
            }
            // Sorts the array prior to binary search
            Arrays.sort(Element.elementSymbols); 
            // check to see if the entered value is in the elementSymbols array
            if(Arrays.binarySearch(Element.elementSymbols,elementsText.getText())<=0){
                elementsText.clear();
                concText.clear();
              Alert.loadMessage("Element not found");
              //Checks to see if entered value is positive and less than 100
            }else if(concInput<0 || concInput>=100){
                elementsText.clear();
                concText.clear();
                Alert.loadMessage("Number is not within range (0-100)");
                // Will add Alert Dialogs when they are released in March 2015
            }else{
                eleTable.add(new Element(elementsText.getText(),Double.parseDouble(concText.getText()),weightPercentRadio.isSelected()));
                elementsText.clear();
                concText.clear();
                Collections.sort(eleTable,Element.elementConcDescComparator);
            }
        });
        // Clears  the TableView
        Button clear = new Button("Clear");
        clear.setOnAction((event)->{
          matchTable.clear();
          root.getRight().setOpacity(0);
           eleTable.clear();
           srcFileName.setText("No file selected");
        });
        
       eleInputHBox.getChildren().addAll(elementsLabel,elementsText,concLabel,
                concText,add,clear);
       
       
       
       
       
        leftVBox.getChildren().addAll(sampleInfoVBox,elementTable,tableControlsHBox, eleInputHBox);
        VBox.setVgrow(elementTable, Priority.ALWAYS);
        root.setLeft(leftVBox);
       
       
       
       
       
       
       
       
       
       VBox centerBox = new VBox();
        root.setCenter(centerBox);
        Button matchSearchButton = new Button("Search");
        centerBox.setAlignment(Pos.CENTER);
        centerBox.getChildren().add(matchSearchButton);
       
        matchSearchButton.setOnAction((e)->{
            matchTable.clear();
            root.getRight().setOpacity(1);
            // Library generated from files selected in preference menu aka search Library
            SampleLibrary sLibrary = new SampleLibrary();
            // used for reading files and adding to sLibrary
            SampleLibrary tempLibrary;
            //Goes through each searchLibrary and adds all the Samples to search Library
            for (String searchLibrary1 : searchLibrary) {
                File tempFile = new File("C:\\Users\\Yan\\Documents\\NetBeansProjects\\EleAnalysis\\library\\" + searchLibrary1);
                tempLibrary = (SampleLibrary) SerializationUtil.deserialize(tempFile);
                // adds all samples in file to search Library
                for(int j = 0; j < tempLibrary.getArrayList().size(); j++){
                    sLibrary.getArrayList().add(tempLibrary.getArrayList().get(j)); 
                }
            }
            
            new Thread(new Runnable(){

                @Override
                public void run() {
                    
                    for(int k = 0; k < sLibrary.getArrayList().size(); k++){
                        ArrayList <Element> eleTableList = new ArrayList(Arrays.asList(eleTable.toArray()));
                        double matchPercent = Match.calculateMatchPercent(new Sample(eleTableList,samName.getText(),       
                        weightPercentRadio.isSelected()), sLibrary.getArrayList().get(k));
                        DecimalFormat df = new DecimalFormat("##.#");
                       matchPercent = Double.parseDouble(df.format(matchPercent));
                        if( matchPercent > 0){
                            matchTable.add(new Match(sLibrary.getArrayList().get(k).getName(),
                                    matchPercent, sLibrary.getArrayList().get(k)));        
                        }
                    }
                    Collections.sort(matchTable,Match.descendingMatchComparator);
                }
                
            }).start();
            
        });
       
     
        
        
        
        
        
        
        
        
        
       
       VBox rightVBox = new VBox();
        VBox matchListVBox = new VBox();
        matchListVBox.setAlignment(Pos.CENTER);
        Label matchListVBoxHeader = new Label("Matches");
        matchListVBox.getChildren().addAll(matchListVBoxHeader, matchList);
        matchListVBox.setPadding(new Insets(5));
        
        
     
        VBox matchSelectionVBox = new VBox();
        matchSelectionVBox.setAlignment(Pos.CENTER);
        Label matchSelectionVBoxHeader = new Label("Select a match");
        matchSelectionVBox.getChildren().addAll(matchSelectionVBoxHeader, matchSelection);
        matchSelectionVBox.setPadding(new Insets(5));
        rightVBox.setPadding(new Insets(10));
        root.setRight(rightVBox);
        rightVBox.setOpacity(0);
        rightVBox.getChildren().addAll(matchListVBox, matchSelectionVBox);
        TableColumn matchNameCol = new TableColumn("Sample Name");
        TableColumn matchPercentCol = new TableColumn(" Match %");
        matchNameCol.setPrefWidth(150);
        matchNameCol.setMaxWidth(200);
        matchNameCol.setCellValueFactory(
                new PropertyValueFactory<>("name"));
        matchPercentCol.setPrefWidth(150);
        matchPercentCol.setMaxWidth(200);
        matchPercentCol.setCellValueFactory(
                new PropertyValueFactory<>("percent"));
        matchList.getColumns().addAll(matchNameCol,matchPercentCol);
        matchList.setMaxWidth(350);
        matchList.setMaxHeight(175);
        matchList.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        matchList.setItems(matchTable);
       // matchList.setPadding(new Insets(5));
        //This responds to clicks on the list of matches. If an item is selected it is displayed
        // on the Table below
        matchList.getSelectionModel().getSelectedItems().addListener(new ListChangeListener(){

            @Override
            public void onChanged(ListChangeListener.Change c) {
                
                    
                ObservableList<Element> selectedSample = FXCollections.observableArrayList();

                if(!matchList.getSelectionModel().isEmpty()){ // this if statement prevents 
                    Sample temp = new Sample(matchList.getSelectionModel().getSelectedItem().getArray(),"temp",
                        matchList.getSelectionModel().getSelectedItem().getArray().get(0).isWP());
                    temp.getArrayCopy().stream().forEach((Element e)->selectedSample.add(e));
                    Collections.sort(selectedSample,Element.elementConcDescComparator);
                    matchSelection.setItems(selectedSample);
                    matchSelectionVBoxHeader.setText(matchList.getSelectionModel().getSelectedItem().getName());
                }    
            }          
        });
        
        TableColumn matchElement = new TableColumn("Element");
        TableColumn matchConcentration = new TableColumn(" Conc %");
        matchElement.setPrefWidth(150);
        matchElement.setMaxWidth(200);
        matchElement.setCellValueFactory(
                new PropertyValueFactory<>("elementName"));
        matchConcentration.setPrefWidth(150);
        matchConcentration.setMaxWidth(200);
        matchConcentration.setCellValueFactory(
                new PropertyValueFactory<>("concWeight"));
        matchSelection.getColumns().addAll(matchElement, matchConcentration);

       
        
        
        
        
        
       
       
        //Preferences Menu Option
        Stage preferencesStage = new Stage();
        VBox preferencesRoot = new VBox();
        Scene preferencesScene = new Scene(preferencesRoot, 300,300);
        preferencesStage.setScene(preferencesScene);
        preferencesStage.setResizable(false);
        preferencesStage.setTitle("Preferences");
        Rectangle outsideBorder = new Rectangle(10,10,preferencesScene.getWidth()-20,preferencesScene.getHeight()-20);
        //Sets up selector for data type
        
        HBox libraryFileName = new HBox();
        libraryFileName.setPadding(new Insets(10,5,5,10));
       
        libraryFileName.getChildren().addAll(prefLibraryLabel,prefLibraryTextField);
        // Exit out of preferences
        
        VBox exitP = new VBox();
        exitP.setAlignment(Pos.CENTER);
        exitP.setPadding(new Insets(10));
        Button exitPref = new Button("Save and Exit");
        exitPref.setOnAction((e)->{
            
            preferencesStage.close();
        });
        exitP.getChildren().add(exitPref);
        
       
        //ListView for Selecting Files
        VBox libLV = new VBox(); // encapsulates library listview
        Label libHeader = new Label("Libraries");
        ListView<String>libraryLV = new ListView<>();
        libraryLV.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        libraryLV.setMaxSize(100,200);
        libLV.setAlignment(Pos.CENTER);
        libLV.setPadding(new Insets (15));
        libLV.getChildren().addAll(libHeader,libraryLV);
        
        // ListView That contains libraries that have been added
        VBox srchLV = new VBox();
        Label searchHeader = new Label("Selected Libraries");
        ListView<String>searchLV = new ListView<>();
        searchLV.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        searchLV.setMaxSize(100,200);
        srchLV.setAlignment(Pos.CENTER);
        srchLV.setPadding(new Insets(15));
        srchLV.getChildren().addAll(searchHeader, searchLV);
        
        HBox libManagement = new HBox();
        
        Button addToSearch = new Button("+");
        addToSearch.setOnAction((e)->{ // Adds selections from libraryLV to searchLV
            MultipleSelectionModel <String> selected =  libraryLV.getSelectionModel();
            for(String s:selected.getSelectedItems()){
                if(!searchLibrary.contains(s)){
                    searchLibrary.add(s);
                }
            }
        });
        
        Button removeFromSearch = new Button("-");
        removeFromSearch.setOnAction((e)->{ //removes selections from searchLV
            MultipleSelectionModel <String> selected =  searchLV.getSelectionModel();
                for(String s: selected.getSelectedItems()){
                    searchLibrary.remove(s);
                }
        });
        
        libManagement.getChildren().addAll(libLV,addToSearch,removeFromSearch,srchLV);
        preferencesRoot.getChildren().addAll( libraryFileName, libManagement,exitP);
        
        
        
      
        
        
        
        //Button to run a match search
        
        
        //Results Table
         
        
        //Adding all the menus and menu items
        final List<File> files = new ArrayList<>();
        final FileChooser fileChooserExcel = new FileChooser();
        fileChooserExcel.setInitialDirectory(new File(System.getProperty("user.home"))); 
        fileChooserExcel.setTitle("Open Resource File");
        fileChooserExcel.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("XLS", "*.xls"),
                new FileChooser.ExtensionFilter("XLSX", "*.xlsx")
        );  
        MenuBar menuBar = new MenuBar();
        Menu menuFile = new Menu("File");
        Menu menuEdit = new Menu("Edit");
        Menu menuView = new Menu("View");
        MenuItem openSpreadSheet = new MenuItem("Open Spreadsheet");
        MenuItem openLibrary = new MenuItem("Open Library");
        MenuItem saveLibrary = new MenuItem("Save to Main Library");
        MenuItem saveToReport = new MenuItem("Create Report");
        MenuItem close = new MenuItem("Close");
        MenuItem preferences = new MenuItem("Preferences");
        openSpreadSheet.setOnAction((event)->{
            menuItemOpen(eleTable, files,fileChooserExcel, weightPercentRadio, srcFileName);
        }); //response when user tries to open library
        openLibrary.setOnAction((event)->{
                
                menuItemOpenLibrary(fileChooserLib, eleTable, srcFileName);
            
        });
        saveLibrary.setOnAction((ActionEvent event)->{try { //response when user selects save to Library
            menuItemSave(files, prefLibraryTextField.getText());
            } catch (IOException ex) {
                Logger.getLogger(EleAnalysis.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        saveToReport.setOnAction((e)->{
            if(cachedLib.getArrayList().size()>0)
                try {
                    cachedLib.writeReport(stage);
            } catch (FileNotFoundException ex) {
                Logger.getLogger(EleAnalysis.class.getName()).log(Level.SEVERE, null, ex);
            }
            else 
                Alert.loadMessage("No samples selected");
        });
        preferences.setOnAction((event)->{
            File file = new File("C:\\Users\\Yan\\Documents\\NetBeansProjects\\EleAnalysis\\library\\");
            File[] libraryList = file.listFiles();
            libraryTable.clear();
            for(File f: libraryList){
                libraryTable.add(f.getName());
            }
            searchLV.setItems(searchLibrary);
            libraryLV.setItems(libraryTable);

            
            preferencesStage.show();
        });
        close.setOnAction((ActionEvent event)->{
            stage.close();});
        menuFile.getItems().addAll(openSpreadSheet,openLibrary,saveLibrary, saveToReport, close);
        menuEdit.getItems().addAll(preferences);
        menuBar.getMenus().addAll(menuFile, menuEdit, menuView);
        root.setTop(menuBar);
        
        
        
        
            
        
        //TextField that will contain sample name
        
        
        
        
        //root.setBottom(hb1);
       
        
        
       
        //Text boxes/buttons below Table that interact with table
        
        // formatting
        BorderPane.setMargin(leftVBox, new Insets(0,10,0,10));
        HBox.setMargin(eleInputHBox, new Insets(2,2,2,2));
        VBox.setMargin(sampleInfoHBox, new Insets(5,5,5,5));
        
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
        
    }
    public void menuItemOpen(ObservableList<Element> eleTable, List<File> files, 
            FileChooser spreadSheetFC, RadioButton wpRadio, Label srcFileName) { //Opens an Excel File
        // If there are spreadsheets in the list item, delete them before adding new ones
        if(files!=null){ 
            files = null;
        }
            files = spreadSheetFC.showOpenMultipleDialog(stage);
            if (files!= null){
                //multiOpen = new SampleLibrary();
               spreadSheetFC.setInitialDirectory(files.get(0).getParentFile()); // Sets the initial directory to where you got your last file form
                for(File file: files){
                    cachedLib.getArrayList().add(new Sample(file,wpRadio.isSelected()));
                }
                updateElementTable(eleTable, 0, srcFileName); // Updates eleTable with first set of data
                eleTableIndex = 0;   
            }
                    
                
            
                        //isOpen = true; // Tells program that something is currently stores in multiOpen so an override cannot occur
                
                
           
            
           
          
           
    }
    private void menuItemOpenLibrary(FileChooser fileChooserLib, 
            ObservableList<Element> eleTable, Label srcFileName) {
   
        File file = fileChooserLib.showOpenDialog(stage);
        if (file != null){
            cachedLib = (SampleLibrary)SerializationUtil.deserialize(file);//samLibrary = new SampleLibrary(file);
       
            updateElementTable(eleTable , 0, srcFileName);
            eleTableIndex = 0;
        }
        
    }
    
    
    
    private void menuItemSave(List <File> files, String libName) throws IOException{
    
        File file = new File("C:\\Users\\Yan\\Documents\\NetBeansProjects\\EleAnalysis\\library\\"+
                libName+".dat");
        Sample tempSam;
        if (files!= null){
            addSampleLibraryToLibrary(file, cachedLib);
            //SerializationUtil.serialize(multiOpen, file);
            /*for(int i= 0; i < multiOpen.getLength();i++){
                
                //multiOpen.writeFile(multiOpen.getArray()[i]);
                       // .getArray()[i].writeToFile(file);
                SerializationUtil.serialize(multiOpen.getArray()[i], file);
                
            }
            */
        }
        
    }
    public void tableViewSave(ObservableList<Element> elTable, int index, String fileName, String samName, RadioButton wpRadio) {
        File file = new File("C:\\Users\\Yan\\Documents\\NetBeansProjects\\EleAnalysis\\library\\" + 
                fileName+".dat");
       
        ArrayList<Element> ele = new ArrayList<>();
        for (Element eleTable1 : elTable) {
            ele.add(new Element(eleTable1.getElementName(), eleTable1.getConc(), wpRadio.isSelected()));
        }
        // If the data
        
        addSampleToLibrary(file,new Sample(ele, samName,wpRadio.isSelected()));
        
        
    }
    // adding a Sample to the main.dat library
    private void addSampleToLibrary(File file, Sample sam){
        SampleLibrary tempSamLib ;
        if(!file.exists()){ // Don't try to deserialize if there is no file
            file = new File(file.getPath());
            tempSamLib = new SampleLibrary();
        } 
        else{
            
                tempSamLib = (SampleLibrary)SerializationUtil.deserialize(file);
                
        }
       
        tempSamLib.getArrayList().add(sam);
        
        SerializationUtil.serializeOverwrite(tempSamLib, file);
        
    }
    // Adding a sample library to the main.dat library
    private void addSampleLibraryToLibrary(File file, SampleLibrary sl){
        SampleLibrary tempSamLib = new SampleLibrary();
        
         if(file.exists()){
            tempSamLib = (SampleLibrary)SerializationUtil.deserialize(file);
         }

        // Adds all the samples of the parameter sl to Sample Library to be written to main.dat
     
        //for(int i = 0; i < sl.getArrayList().size(); i++){
        //    tempSamLib.getArrayList().add(sl.getArrayList().get(i));
        //}

       SerializationUtil.serializeOverwrite(
               SampleLibrary.mergeLibraries(sl, tempSamLib), file);

    }
    
    private void updateElementTable(ObservableList<Element> eleTable, int index, Label fileName){
        eleTable.setAll(cachedLib.getArrayList().get(index).getArrayCopy());
       /*eleTable.clear();
       ArrayList <Element> temp = new ArrayList<>();
       for (Element el: sl.getArrayList().get(index).getArray()){
           temp.add(new Element(el.getName(),el.getConc(),weightPercentRadio.isSelected()));
       }
       Sample tempSample = new Sample(temp,"temp", weightPercentRadio.isSelected());
       eleTable.addAll(tesmpSample.getArray());*/
       eleTableIndex = index;
       fileName.setText(cachedLib.getArrayList().get(eleTableIndex).getName());
              // list.get(eleTableIndex).getName());
       Collections.sort(eleTable,Element.elementConcDescComparator);
    }
    
    
}
