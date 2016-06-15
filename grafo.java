public class grafo {
	int vertices;
	int arestas;
	int matriz [][];
	int memoization [][];
	Random aleatorio = new Random();
	int explorado [];          
	int ciclo [];               
	int min;                    
	int melhorCiclo [];        
	int nivel;          
	
	
	public grafo(int n,int m){
		min = 1000000;
		vertices = n;
		arestas = m;
		int aux = (int)Math.pow(2, n) - 1;
		matriz = new int [n][n];
		memoization = new int [n][aux];
		explorado = new int [vertices];
		ciclo = new int[vertices +1];
		melhorCiclo = new int[vertices+1];
		
		for (int i = 0; i < n; i++)
			for(int j = 0; j < n; j++)
				matriz[i][j] = 9999;
		
		for (int i = 0; i < n; i++)
			for(int j = 0; j < aux; j++)
				memoization[i][j] = -1;
		
		for(int i=0; i<vertices;i++)
            explorado[i]=0;
		
		for(int i = 0; i < vertices+1; i++){
			ciclo [i] = 0;
			melhorCiclo [i] = 0;
		}
		
        nivel=0;    /* inicializa nivel */
        min=1000000;/* inicializa min */
		
	}
	
	void geraGrafo (){
		for (int i = 0; i < arestas; i++)
			geraAresta();
	}
	void geraAresta(){
		int v1 = -1;
		int v2 = -1;
		int acrecentada = 0;
		do{
			while(v1 == v2){
				v1 = aleatorio.nextInt(vertices);
				v2 = aleatorio.nextInt(vertices);
			}
			if (matriz[v1][v2] == 9999){
				matriz[v1][v2] = 1 + aleatorio.nextInt(10);
				matriz [v2][v1] = matriz[v1][v2];
				acrecentada = 1;
			}
			else v1 = v2 = -1;
		}while (acrecentada == 0);
	}
	void imprimirMatriz (){
		for (int i = 0; i < vertices; i++) {
		   for (int j = 0; j < vertices; j++) {
		       System.out.print(matriz[i][j]+" ");
		   }
		   System.out.print("\n");
		}
	}
	
	int CV (int c, int b){
		if(b == ((int)(Math.pow(2,vertices))-1))
			return matriz[c][0];
		else if(memoization[c][b] != -1)
			return memoization[c][b];
		int resposta = 9999;
		for(int i = 1; i < vertices; i++){
		   if((int)((b/Math.pow(2, i))%2)==0){
					int a = CV(i, b + (int)Math.pow(2, i));
					if(matriz[c][i] + a < resposta)
						resposta = matriz[c][i] + a;
			}
		}
		memoization [c][b] = resposta;
		return resposta;
	}

	private int medeciclo(int[] t) {
        int i;
        int l=0;
        for(i=0;i<vertices-1;i++)
            l=l+matriz[t[i]][t[i+1]];
            l=l+matriz[t[vertices-1]][t[0]];
        return l;
    }
	
	private void dfs(int v, int nivel) {
        int i,j,dist;
        explorado[v] = 1;
        ciclo[nivel] = v;
        if(nivel==(vertices-1)){
            dist = medeciclo(ciclo);
            if(dist < min){
                min = dist;
                for(j=0; j<vertices; j++)
                    melhorCiclo[j]=ciclo[j];
            }
        }
        for(i=0;i<vertices;i++){
            if(explorado[i]!=1){
                dfs(i,nivel+1);
                explorado[i]=0;
            }
        }
    }
	
	public static void main(String[] args ){
		grafo g = new grafo(6,10);
		int distanciaTotal;
		g.geraGrafo();
		g.imprimirMatriz();
		Date comeco1 = new Date();
		long horaDoComeco1 = comeco1.getTime();
		distanciaTotal = g.CV(0,1);
		Date final1 = new Date();
		long horaDoFinal1 = final1.getTime();
		long tempo1 = horaDoFinal1 - horaDoComeco1;
		System.out.println("tempo1 = " + tempo1);
		System.out.println("RESPOSTA = " + distanciaTotal);
		Date comeco2 = new Date();
		long horaDoComeco2 = comeco2.getTime();
		g.dfs(0, 0);
		Date final2 = new Date();
		long horaDoFinal2 = final2.getTime();
		long tempo2 = horaDoFinal2 - horaDoComeco2;
		System.out.println("tempo2 = " + tempo2);
		System.out.println("Melhor Ciclo:" );
		for(int i = 0; i < g.vertices +1;i ++)
			System.out.println(+ g.melhorCiclo[i]);
	}
}
