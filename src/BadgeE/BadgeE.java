package BadgeE;

public class BadgeE
{
    public static int getModifiedMarque(int tabMarques[],int N){
        int test = (int) (Math.random() * (N - 2) + 1);
        while( tabMarques[test] != 1){
            test = (int) (Math.random() * (N - 2) + 1);
        }
        return test;
        
    }
    
    public static int[] chooseNewConfiguration(int[] newTabMarques,int[] tabMarques,int score,int newScore){
        double T = 0.5;
        if(newScore < score){
            return newTabMarques;
        }
        else{
            double changeThreashold = Math.exp((score - newScore)/T);
            System.out.println("Seuil : "+changeThreashold);
            double rand = Math.random();
            if(rand <= changeThreashold){
                return newTabMarques;
            }
            else{
                return tabMarques;
            }
        }
        
    }
    
    
    public static int[] changeMarque(int[] tabMarques,int marqueToChange,int N){
        int[] newTabMarques = tabMarques;
        boolean validChange = false;
        int random=0;
        while(!validChange){
            random = (int) (Math.random() * N );
            if(tabMarques[random] == 0){
                newTabMarques[marqueToChange] = 0;
                
                newTabMarques[random]=1;
                validChange =true;
            }
        }
        return newTabMarques;
    }
    
    public static int[] initTabMarques(int N, int K)
    {
        int[] tabMarques = new int[N];
        int random = 0;
        
        tabMarques[0] = 1;
        tabMarques[N-1] = 1;
        
        for(int i = 0 ; i < K - 2  ; i++)
        {
            random = (int) (Math.random() * N );
            
            if(tabMarques[random] == 0)
            {
                tabMarques[random] = 1;
            }
            else
            {
                i--;
            }
        }
        
        return tabMarques;
    }
    
    public static int[][] getDistanceMatrix(int[] tabMarques, int N)
    {
        int[][] distanceMatrix = new int[N][N];
        
        for(int i = 0 ; i < N ; i++)
        {
            for(int j = 0 ; j < N ; j++)
            {
                distanceMatrix[i][j] = 0;
            }
        }
        
        for(int i = 0 ; i < N ; i++)
        {
            for(int j = i + 1 ; j < N ; j++)
            {
                if(tabMarques[i] == 1 && tabMarques[j] == 1)
                {
                    distanceMatrix[i][j] = Math.abs(i-j);
                    distanceMatrix[j][i] = Math.abs(i-j);
                }
            }
        }
        return distanceMatrix;
    }
    
    public static int[] getEqualityTab(int[][] distanceMatrix, int N)
    {
        //Initialisation du tableau des scores
        int[] equalityTab = new int[N];
        for(int i = 0 ; i < N ; i++)
        {
            equalityTab[i] = 0;
        }
        
        //Parcours du tableau des distances et remplissage du tableau des scores
        for(int x = 0 ; x < N - 1 ; x++)
        {
            for(int y = x + 1 ; y < N ; y++)
            {
                if(distanceMatrix[x][y] != 0){
                    equalityTab[distanceMatrix[x][y]] ++;

                }
            }
        }

        return equalityTab;
    }
    
    public static boolean testEqualityTab(int[] equalityTab, int N)
    {        
        for(int i = 0 ; i < N ; i++)
        {
            if(equalityTab[i] > 1)
            {
                return false;
            }
        }
        
        return true;
    }
    
    public static int[] getScoreAndDistanceMax(int[] equalityTab, int N)
    {
        //On stocke le score dans la case 0 et la distanceMax dans la case 1
        int resultat[] = new int[2];
        resultat[0] = 0;
        int distanceMax = 0;
        
        for(int i = 0 ; i < N ; i++)
        {
            if(equalityTab[i] > 1 )
            {
                resultat[0] += equalityTab[i];
                
                if(equalityTab[i] > distanceMax)
                {
                    distanceMax = i;
                }    
            }
        }
        
        resultat[1] = distanceMax;
        
        return resultat;
    }
    
    public static int getPivot(int distanceMax, int[][] distanceMatrix, int N)
    {
        int pivot = 0;
        int pivotTab[] = new int[N];
        for(int i=0; i<N; i++){
            pivotTab[i]=0;
        }
        //Parcours du tableau des distances pour trouver le pivot
        for(int x = 0 ; x < N - 1 ; x++)
        {
            for(int y = x + 1 ; y < N ; y++)
            {
                if(distanceMatrix[x][y] == distanceMax)
                {
                    pivotTab[x]++;
                    pivotTab[y]++;
                }
            }
        }
        
        double rand = 0;
        int pivotScore=0;
        for(int i=1; i<N-1; i++){
            if(pivotScore<pivotTab[i]){
                pivot = i;
                pivotScore = pivotTab[i];
            }
            if(pivotScore == pivotTab[i]){
                rand = Math.random();
                if(rand >= 0.5){
                    pivot = i;
                    pivotScore = pivotTab[i];
                }
            }
        }
        return pivot;
    }
    
    public void modifPivot()
    {
        
    }
    
    public static void main (String args[])
    {
        int N = 18;
        int K = 6;
        int score = 999;
        int newScore = 999;
        int[][] distanceMatrix;
        int equalityTab[];
        int[] resultat;
        int distanceMax;
        int pivot;
        
        int[][] newDistanceMatrix;
        int newEqualityTab[];
        int[] newResultat;
        int newDistanceMax;
        int newPivot;
        int nbTurn=0;   
        int[] tabMarques = initTabMarques(N, K);
        do{
            System.out.println("Tableau des marques : ");
            for(int i = 0 ; i < N ; i++)
            {
                System.out.print("[");
                System.out.print(tabMarques[i]);
                System.out.print("]");
            }
            System.out.println("\n");

            //Tableau de l'énoncé
            //int tab[] = {1,1,0,0,1,0,1};

            distanceMatrix = getDistanceMatrix(tabMarques, N);
            System.out.println("Matrice des distances : ");
            for(int i = 0 ; i < N ; i++)
            {
                for(int j = 0 ; j < N ; j++)
                {
                    System.out.print("[");
                    System.out.print(distanceMatrix[i][j]);
                    System.out.print("]");
                }
                System.out.println();
            }
            System.out.println("\n");


            equalityTab = getEqualityTab(distanceMatrix, N);
            System.out.println("Tableau des égalités : ");
            for(int i = 0 ; i < N ; i++)
            {
                System.out.print("[");
                System.out.print(equalityTab[i]);
                System.out.print("]");
            }
            System.out.println("\n");

            resultat = getScoreAndDistanceMax(equalityTab, N);
            score = resultat[0];
            distanceMax = resultat[1];
            System.out.println("Score : " + score);
            System.out.println("Distance la plus fréquente : " + distanceMax);        

            pivot = getPivot(distanceMax, distanceMatrix, N);
            System.out.println("Pivot : " + pivot);
            //int test = getModifiedMarque(tabMarques,N);
            int[] newTabMarques = changeMarque(tabMarques,pivot,N);
            System.out.println("Nouveau tableau des marques : ");
            for(int i = 0 ; i < N ; i++)
            {
                System.out.print("[");
                System.out.print(newTabMarques[i]);
                System.out.print("]");
            }
            System.out.println("\n");
            newDistanceMatrix = getDistanceMatrix(newTabMarques, N);
            System.out.println("Matrice des distances de la configuration candidate: ");
            for(int i = 0 ; i < N ; i++)
            {
                for(int j = 0 ; j < N ; j++)
                {
                    System.out.print("[");
                    System.out.print(newDistanceMatrix[i][j]);
                    System.out.print("]");
                }
                System.out.println();
            }
            System.out.println("\n");


            newEqualityTab = getEqualityTab(newDistanceMatrix, N);
            System.out.println("Tableau des égalités de la configuration candidate : ");
            for(int i = 0 ; i < N ; i++)
            {
                System.out.print("[");
                System.out.print(newEqualityTab[i]);
                System.out.print("]");
            }
            System.out.println("\n");

            newResultat = getScoreAndDistanceMax(newEqualityTab, N);
            newScore = newResultat[0];
            newDistanceMax = newResultat[1];
            System.out.println("Score du candidat : " + newScore);
            System.out.println("Distance la plus fréquente du candidat : " + newDistanceMax);
            tabMarques = chooseNewConfiguration(newTabMarques,tabMarques,score,newScore); 
            nbTurn++;
            System.out.println("Nombre de tour : "+nbTurn);
            
       }while( newScore !=0  && score != 0);
        System.out.println("\n------Marques solutions------");
        System.out.print("|");
        for(int i = 1; i < N-1 ; i++){
            if(tabMarques[i] != 0){
                System.out.print(" "+i+" ");
            }
            else{
                System.out.print("-");
            }
        }
        System.out.print("|\n");

        
      
        

        
        

        
        
        
        //searchGolomb(2,3);
    }
}