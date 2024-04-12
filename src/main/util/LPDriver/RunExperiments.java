package main.util.LPDriver;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import main.operator.selection.SelectionOperator;
import main.operator.selection.selectionClasses.PrometheeIISelection;
import main.operator.selection.selectionClasses.TopsisSelection;
import main.solution.solutionSet;
import main.util.ranking.Criteria;

@SuppressWarnings("unused")
public class RunExperiments {

	
	@SuppressWarnings({ "rawtypes", "unused", "unchecked" })
	public static void main(String[] args) throws IOException {
		
		//File file = new File("C:/Users/akinola/OneDrive - Robert Gordon University/Documents/IWSP_VAP_MTSP");
		
		//File[] files = file.listFiles();
		
		File file_sflp = new File("C:/Users/akinola/OneDrive - Robert Gordon University/Documents/Datapipe/InputData/SFLP");
		
		File[] files_sflp = file_sflp.listFiles();
		
	
		int a =0;
		for(int i = 0; i<files_sflp.length; i++) {
			
			for(int j = 0; j < files_sflp[i].listFiles().length; j++) {
				a++;
			}
			//args2[i] = files[i].getName();
		}
		args = new String[a];
		String[] args2_sflp = new String[a];
		int x = 0;
		for(int i = 0; i<files_sflp.length; i++) {
			
			for(int j = 0; j < files_sflp[i].listFiles().length; j++) {
				if(files_sflp[i].listFiles()[j].length() == 0)continue;
				//System.out.println(files_sflp[i].listFiles()[j]);
				args[x] = files_sflp[i].listFiles()[j].toString();
				args2_sflp[x] = files_sflp[i].listFiles()[j].getName();
				x++;
			}
			
		}
		
		File file_spfsp = new File("C:/Users/akinola/OneDrive - Robert Gordon University/Documents/Datapipe/InputData/SPFSP");
		
		File[] files_spfsp = file_spfsp.listFiles();
		
		int b = 0;
		for(int i = 0; i<files_spfsp.length; i++) {
			
			for(int j = 0; j < files_spfsp[i].listFiles().length; j++) {
				b++;
			}
			//args2[i] = files[i].getName();
		}
		
		String[] args_spfsp = new String[b];
		String[] args2_spfsp = new String[b];
		int y = 0;
		for(int i = 0; i<files_spfsp.length; i++) {
			
			for(int j = 0; j < files_spfsp[i].listFiles().length; j++) {
				if(files_spfsp[i].listFiles()[j].length() == 0)continue;
				//System.out.println(files_spfsp[i].listFiles()[j]);
				args_spfsp[y] = files_spfsp[i].listFiles()[j].toString();
				args2_spfsp[y] = files_spfsp[i].listFiles()[j].getName();
				y++;
			}
			//args2[i] = files[i].getName();
		}
		
	//	System.out.println(args.length + " " + args_spfsp.length);
		
		//System.out.println();
		List<Criteria> criteria = new ArrayList<>();
		
		criteria.add(new Criteria("sflp_Obj_1", true));
		criteria.add(new Criteria("sflp_Obj_2", true));
		criteria.add(new Criteria("sflp_Constraint_1", true));
		criteria.add(new Criteria("sflp_Constraint_2", true));
		criteria.add(new Criteria("spfsp_Obj_1", true));
		criteria.add(new Criteria("spfsp_Obj_2", true));
		
		SelectionOperator<List<solutionSet>, List<solutionSet>> selectionOperator1 = new TopsisSelection<solutionSet>(20, criteria);
		
		SelectionOperator<List<solutionSet>, List<solutionSet>> selectionOperator2 = new PrometheeIISelection<solutionSet>(20, criteria);
		
		//RunMcrgalpSflpSpfsp topsis = new RunMcrgalpSflpSpfsp();
    	
		
		//topsis.MCRGALP_SFLP_SPFSP_Experiments("Topsis", selectionOperator1, criteria, args, args2_sflp, args_spfsp, args2_spfsp);
		
		
		//RunMcrgalpSflpSpfsp promethee = new RunMcrgalpSflpSpfsp();
		
		
		//promethee.MCRGALP_SFLP_SPFSP_Experiments("Promethee_II", selectionOperator2, criteria, args, args2_sflp, args_spfsp, args2_spfsp);
		
		RunNsgalpIISflpSpfsp nsga = new RunNsgalpIISflpSpfsp();
		
		nsga.NSGALP_SflpSpfspExperiments(args, args2_sflp, args_spfsp, args2_spfsp);	
		
		RunNsgalpIIISflpSpfspExperiments nsgaIII = new RunNsgalpIIISflpSpfspExperiments();
		
		nsgaIII.NSGALP_SflpSpfspExperiments(args, args2_sflp, args_spfsp, args2_spfsp);
		
		//MCRGALP_Topsis_Fitness_SFLP_SPFSP
		//MCRGALP_Topsis_Var_SFLP_SPFSP
		//MCRGALP_Promethee_II_Fitness_SFLP_SPFSP
		//NSGALP_II_Fitness_SFLP_SPFSP
		//NSGALP_III_Fitness_SFLP_SPFSP
		//NSGALP_II_Var_SFLP_SPFSP
		//NSGALP_III_Var_SFLP_SPFSP
		//MCRGALP_Promethee_II_Var_SFLP_SPFSP
	//	RunSequenceAlgorithmsIwspVapMtsp iwsp_vap_mtsp1 = new RunSequenceAlgorithmsIwspVapMtsp();
	//	iwsp_vap_mtsp1.SEQUENCE_IwspVapMtspExperiments(args, args2);
		
		
		//RunMCRGALPiwsp_vap_mtspExperiments iwsp_vap_mtsp = new RunMCRGALPiwsp_vap_mtspExperiments();
		//iwsp_vap_mtsp.MCRGALP_IwspVapMtspExperiments(args, args2);
		
		
		//RunNSGALPiwsp_vap_mtspExperiments iwsp_vap_mtsp2 = new RunNSGALPiwsp_vap_mtspExperiments();
		//iwsp_vap_mtsp2.NSGALP_IwspVapMtspExperiments(args, args2);
		//RunKpJapExperiments kp_jap = new RunKpJapExperiments();
		//kp_jap.KpJapExperiments();
		//RunKpPfspExperiments kp_pfsp = new RunKpPfspExperiments();
		//kp_pfsp.KpPfspExperiments();
		//RunKpTspExperiments kp_tsp = new RunKpTspExperiments();
		//kp_tsp.KpTspExperiments(); 
		
		//RunFlpJapExperiments flp_jap = new RunFlpJapExperiments();
		//flp_jap.FlpJapExperiments();
		//RunQapTspExperiments qap_tsp = new RunQapTspExperiments();
		//qap_tsp.QapTspExperiments();
		
	}
}
