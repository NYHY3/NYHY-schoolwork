import java.util.*;
import static java.lang.System.out;

class node
{
    public int ts, tn ,tm,step;//当前各瓶内含液体体积,step为已过步数

    node(){}

    node(int ts,int tn ,int tm,int step)
    {
        this.ts=ts;
        this.tn=tn;
        this.tm=tm;
        this.step=step;
    }

}

public class test 
{
    int S,N,M,half;

    void getData(){
        Scanner input=new Scanner(System.in);
        S=input.nextInt();
        N=input.nextInt();
        M=input.nextInt();
        //检查输入
        if(S%2==1){ 
            out.println("NO");
            System.exit(0);
        }

        if(S<=0||M<=0||N<=0||S!=N+M){
            out.println("异常输入");
            System.exit(0);
        }  
        
        

        half=S/2;
    }


    node pour(int ts,int tn,int tm,int step,int op){//按op倒水，并step+1

        node temp=new node(ts,tn,tm,step);

        if(op==0){//S->N
            temp.ts=Math.max(ts-(N-tn),0);
            temp.tn=Math.min(N,ts+tn);
        }
        else if(op==1){//S->M
            temp.ts=Math.max(ts-(M-tm),0);
            temp.tm=Math.min(M,ts+tm);
        }
        else if(op==2){//N->S
            temp.tn=Math.max(tn-(S-ts),0);
            temp.ts=Math.min(S,ts+tn);
        }
        else if(op==3){//N->M
            temp.tn=Math.max(tn-(M-tm),0);
            temp.tm=Math.min(M,tm+tn);
        }
        else if(op==4){//M->S
            temp.tm=Math.max(tm-(S-ts),0);
            temp.ts=Math.min(S,ts+tm);
        }
        else if(op==5){//M->N
            temp.tm=Math.max(tm-(N-tn),0);
            temp.tn=Math.min(N,tm+tn);
        }

        temp.step=step+1;

        return temp;
    }


    void check(node cur){//看看是否平分，是就显示step并终止程序
        if((cur.ts==half&&cur.tn==half)||(cur.ts==half&&cur.tm==half)||(cur.tm==half&&cur.tn==half)){//成功平分
            System.out.println(cur.step);
            System.exit(0);
        }

    }
        

    



    void bfs()
    {
        boolean [][][]isRepeat=new boolean[100][100][100];//数组坐标就是液体体积

        for(boolean [][]arr1:isRepeat)
        {
            for(boolean []arr2:arr1)
            {
                Arrays.fill(arr2,false);
            }
        }

        Queue<node> newStatus=new LinkedList<>();

        node firstNode=new node(S,0,0,0);

        newStatus.clear();
        newStatus.offer(firstNode);
        isRepeat[S][0][0]=true;

        while(!newStatus.isEmpty()){

            node t=newStatus.poll();

            for(int i=0;i<6;i+=1){
                node cur=pour(t.ts,t.tn,t.tm,t.step,i);

                if(isRepeat[cur.ts][cur.tn][cur.tm]){//已重复的状态

                    continue;//跳过下面步骤
                }

                //新的状态
                check(cur);

                //cur没平分
                isRepeat[cur.ts][cur.tn][cur.tm]=true;

                newStatus.offer(cur);
            }

        }

        System.out.println("No");//对列已空但仍未平分

    }

    
    public static void main(String[] args)
    {
        test t=new test();
        t.getData();
        t.bfs();
    }
}
