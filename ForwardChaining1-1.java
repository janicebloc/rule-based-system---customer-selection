/**
 * Assignment 2 Section 2 Task 1
 * setupConfig method implementation for the given statements in the assignment, with no changes.
 * 
 */
import java.util.*;
public class ForwardChaining1 {

	    ArrayList<int[]> clauses;
	    
	    public ForwardChaining1() {
	        clauses=new ArrayList<>();
	    }
	    
	    public boolean forwardChaining(int n) {
	        // Simplified version of forward chaining algorithm: does not follow the 
	        // textbook. This implementation does not run in linear time because it 
	        // scans all clauses multiple times. 
	        
	        boolean[] model=new boolean[n+1];    // All symbols set to false initially.
	        
	        //  Sanity check clauses
	        for(int i=0; i<clauses.size(); i++) {
	            int posLits=0;
	            int[] clause=clauses.get(i);
	            for(int j=0; j<clause.length; j++) {
	                assert clause[i]<=n && clause[i]>=-n : "Found reference to variable larger than n.";
	                if(clause[j]>0) {
	                    posLits++;
	                }
	            }
	            
	            assert posLits<=1 : "At most one positive literal is allowed in each clause.";
	        }
	        
	        //  Iterate through set of clauses applying modus ponens until we reach a 
	        //  fixpoint.
	        boolean fixPoint=false;
	        
	        while(!fixPoint) {
	            fixPoint=true;
	            
	            for(int i=0; i<clauses.size(); i++) {
	                int[] clause=clauses.get(i);
	                //  Check all symbols that appear negated in this clause.
	                //  If all true, then apply modus ponens. 
	                
	                boolean allTrue=true;
	                
	                for(int j=0; j<clause.length; j++) {
	                    if(clause[j]<0 && ! model[-clause[j]]) {
	                        allTrue=false;
	                        break;
	                    }
	                }
	                
	                if(allTrue) {
	                    boolean goalClause=true;
	                    for(int j=0; j<clause.length; j++) {
	                        if(clause[j]>0) {
	                            goalClause=false;
	                            if(! model[clause[j]]) {
	                                model[clause[j]]=true;
	                                fixPoint=false;
	                                System.out.println("Inferred "+clause[j]+" with clause "+Arrays.toString(clause));
	                            }
	                            
	                            break;
	                        }
	                    }
	                    if(goalClause) {
	                        // This is a goal clause
	                        System.out.println("No models satisfy all clauses simultaneously. False goal clause: "+Arrays.toString(clause));
	                        return false;
	                    }
	                }
	            }
	        }
	        
	        System.out.println("Model: ");
	        for(int i=1; i<model.length; i++) {
	            System.out.println("Variable "+i+" = "+model[i]);
	        }
	        return true;
	    }
	    
	    public void addClause(int[] c) {
	        clauses.add(c);
	    }
	    
	    public void resetClauses() {
	        clauses.clear();
	    }
	    
   public static void setupConfig() {
	        
	        ForwardChaining1 fc=new ForwardChaining1();
	        //clauses 1-4, "at most" one of the two options
	        fc.addClause(new int[]{-1,-2});
	        fc.addClause(new int[]{-3,-4});
	        fc.addClause(new int[]{-5,-6});
	        fc.addClause(new int[]{-10,-11});
	        //clause 5: if discrete GPU, then 3.0 Hz quad-core
	        fc.addClause(new int[]{-7,2});	
	        //clause 6: if 3.0 GHz CPU, then CPU/memory hot
	        fc.addClause(new int[]{-2,8});
	        //clause 7: if 2.4 GHz CPU and 32 GB memory, then CPU/memory hot
	        fc.addClause(new int[]{-1,-4,8});
	        //clause 8: if discrete GPU, then GPU cluster runs hot
	        fc.addClause(new int[]{-7,9});
	        //clause 9: if 1TB disk, then GPU cluster runs hot
	        fc.addClause(new int[]{-6,9});
	        //clause 10: if both CPU and GPU clusters run hot, then large case
	        fc.addClause(new int[]{-8,-9,11});
	        //clause 11: if 32 GB memory and 1 TB disk, then large case
	        fc.addClause(new int[]{-4,-6,11});

	        boolean modelExists = fc.forwardChaining(11);
	        System.out.println("Model exists: "+modelExists);
	    } 
	    public static void main(String [] args) {
	        
	        setupConfig();
	        
	    }


}



