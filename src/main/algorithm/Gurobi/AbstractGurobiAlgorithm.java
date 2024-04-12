package main.algorithm.Gurobi;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import gurobi.*;
import main.algorithm.Algorithm;
import main.operator.crossover.CrossoverOperator;
import main.operator.mutation.MutationOperator;
import main.operator.selection.SelectionOperator;
import main.problem.Problem;
import main.solution.solution;
import main.util.model.instance.IWSPInstance.IWSPInstance;

/**
 * Gurobi implementation. Line of codes obtained from https://www.gurobi.com and modified for facility locatin problem representation.
 *
 *@author Akinola Ogunsemi <a.ogunsemi1@rgu.ac.uk>
 *
 *@param <S>
 *
 * 
 */

public abstract class AbstractGurobiAlgorithm<S extends solution<?>, R>  implements Algorithm<S, R> {
	
	//protected S exactSolution;
	protected Problem<S> problem ;
	//protected LinkedProblem linkedproblem;
	protected SelectionOperator<List<S>, S> selectionOperator ;
	protected GRBEnv env;
    protected GRBModel model;
    S solution;
    

	
	public R getExactSolution() {
		return (R) this.solution;
	}
	
	
	public void setProblem(Problem<S> problem) {
	  this.problem = problem ;
	}
	
	//public void setProblem(LinkedProblem linkedproblem) {
	//	  this.linkedproblem = linkedproblem ;
	//}
	
	 public AbstractGurobiAlgorithm(Problem<S> problem) {
		    setProblem(problem);
	  }
	 
	 
	 public GRBEnv getGurobiEnv() throws GRBException {
		 return new GRBEnv();
	 }
	 
	 public GRBModel getModel() throws GRBException {
		 return new GRBModel(getGurobiEnv());
	 }
	
		
	public Problem<S> getProblem() {
	  return problem;
	}
	
	public void setResult(Map<Integer, List<Integer>> sol) {
		this.solution = getProblem().createsolution(sol);
	}
	
	public void run() {
		
		try {
			getModel().set(GRB.StringAttr.ModelName, "facility");
			
			double Capacity[] = new double[((IWSPInstance) problem.getData()).getnFacilities()];
		      for(int i = 0; i<Capacity.length; i++) {
		    	  Capacity[i] = ((IWSPInstance) problem.getData()).getFacility(i).getCapacity();	
		    }
			
			int nPlants = ((IWSPInstance) problem.getData()).getnFacilities();
		     int nWarehouses = ((IWSPInstance) problem.getData()).getnCustomers();
			 double transCosts[][] = new double[nWarehouses][nPlants];
		      for(int i = 0; i<transCosts.length; i++) {
		    	  for(int j = 0; j<transCosts[i].length; j++) {
		    		  transCosts[i][j] = ((IWSPInstance) problem.getData()).getFacility(j).getDemandCost(((IWSPInstance) problem.getData()).getCustomer(i)).getCost();	
			      }	
		      }
		      
		      double FixedCosts[] = new double[((IWSPInstance) problem.getData()).getnFacilities()];
		      for(int i = 0; i<FixedCosts.length; i++) {
		    	  FixedCosts[i] = ((IWSPInstance) problem.getData()).getFacility(i).getFixedCost().getCost();		
		      } 
		      
		      double Demand[] = new double[((IWSPInstance) problem.getData()).getnCustomers()];
			    for(int i = 0; i<Demand.length; i++) {
			      Demand[i] = ((IWSPInstance) problem.getData()).getCustomer(i).getDemand();
				} 

		      // Plant open decision variables: open[p] == 1 if plant p is open.
						
			 // Model
			      GRBEnv env = new GRBEnv();
			      GRBModel model = new GRBModel(env);
			      model.set(GRB.StringAttr.ModelName, "facility");

			      // Plant open decision variables: open[p] == 1 if plant p is open.
			      GRBVar[] open = new GRBVar[nPlants];
			      for (int p = 0; p < nPlants; ++p) {
			        open[p] = model.addVar(0, 1, FixedCosts[p], GRB.BINARY, "Open" + p);
			      }

			      // Transportation decision variables: how much to transport from
			      // a plant p to a warehouse w
			      GRBVar[][] transport = new GRBVar[nWarehouses][nPlants];
			      for (int w = 0; w < nWarehouses; ++w) {
			        for (int p = 0; p < nPlants; ++p) {
			          transport[w][p] =
			              model.addVar(0, GRB.INFINITY, transCosts[w][p], GRB.CONTINUOUS,
			                           "Trans" + p + "." + w);
			        }
			      }

			      // The objective is to minimize the total fixed and variable costs
			      model.set(GRB.IntAttr.ModelSense, GRB.MINIMIZE);

			      // Production constraints
			      // Note that the right-hand limit sets the production to zero if
			      // the plant is closed
			      for (int p = 0; p < nPlants; ++p) {
			        GRBLinExpr ptot = new GRBLinExpr();
			        for (int w = 0; w < nWarehouses; ++w) {
			          ptot.addTerm(1.0, transport[w][p]);
			        }
			        GRBLinExpr limit = new GRBLinExpr();
			        limit.addTerm(Capacity[p], open[p]);
			        model.addConstr(ptot, GRB.LESS_EQUAL, limit, "Capacity" + p);
			      }

			      // Demand constraints
			      for (int w = 0; w < nWarehouses; ++w) {
			        GRBLinExpr dtot = new GRBLinExpr();
			        for (int p = 0; p < nPlants; ++p) {
			          dtot.addTerm(1.0, transport[w][p]);
			        }
			        model.addConstr(dtot, GRB.EQUAL, Demand[w], "Demand" + w);
			      }

			      // Guess at the starting point: close the plant with the highest
			      // fixed costs; open all others

			      // First, open all plants
			      for (int p = 0; p < nPlants; ++p) {
			        open[p].set(GRB.DoubleAttr.Start, 1.0);
			      }

			      // Now close the plant with the highest fixed cost
			    //  System.out.println("Initial guess:");
			      double maxFixed = -GRB.INFINITY;
			      for (int p = 0; p < nPlants; ++p) {
			        if (FixedCosts[p] > maxFixed) {
			          maxFixed = FixedCosts[p];
			        }
			      }
			      for (int p = 0; p < nPlants; ++p) {
			        if (FixedCosts[p] == maxFixed) {
			          open[p].set(GRB.DoubleAttr.Start, 0.0);
			          System.out.println("Closing plant " + p + "\n");
			          break;
			        }
			      }

			      // Use barrier to solve root relaxation
			      model.set(GRB.IntParam.Method, GRB.METHOD_BARRIER);

			      // Solve
			      model.optimize();

			      // Print solution
			      Map<Integer, List<Integer>> map = new HashMap<>();
			    //  System.out.println("\nTOTAL COSTS: " + model.get(GRB.DoubleAttr.ObjVal));
			    //  System.out.println("SOLUTION:");
			      for (int p = 0; p < nPlants; ++p) {
			        if (open[p].get(GRB.DoubleAttr.X) > 0.99) {
			        //  System.out.println("Plant " + p + " open:");
			          List<Integer> cust = new ArrayList<>();
			          for (int w = 0; w < nWarehouses; ++w) {
			            if (transport[w][p].get(GRB.DoubleAttr.X) > 0.0001) {
			           //   System.out.println("  Transport " +
			            //      transport[w][p].get(GRB.DoubleAttr.X) +
			            //      " units to warehouse " + w);
			              cust.add(w);
			            }
			          }
			          map.put(p, cust);
			        } else {
			         // System.out.println("Plant " + p + " closed!");
			        }
			      }
			      setResult(map);
			      // Dispose of model and environment
			      model.dispose();
			      env.dispose();

			    } catch (GRBException e) {
			      System.out.println("Error code: " + e.getErrorCode() + ". " +
			          e.getMessage());
			    }
			      
	}
	
}
