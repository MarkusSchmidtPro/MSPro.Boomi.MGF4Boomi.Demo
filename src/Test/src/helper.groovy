import com.boomi.document.scripting.DataContext
import groovy.json.JsonOutput

class Helper {
    public static void printTextDocuments(DataContext dataContext) {
        int docNo = 0;
        for (def doc in dataContext.Documents) {
            println("Doc[${docNo++}]=" + doc.toString())
        }
    } 
    
    public static void printJsonDocuments(DataContext dataContext) {
        int docNo = 0;
        for (def doc in dataContext.Documents) {
            println("Doc[${docNo++}]=" + JsonOutput.prettyPrint(doc.toString()))
        }
    }
}