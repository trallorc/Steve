import java.awt.*;

public class Units
{
	private static final int N=200;
    protected BasicObject[] objects=new BasicObject[N];
    protected int num=0;
   
    
    public Units()
    {
    }
    

public void draw (Graphics g){
  	 
	for (int i=0;i<num;i++)
		objects[i].draw(g);
  }

    public boolean add(BasicObject ob)
    {
    	if (this.num<N)
    	{
    	  objects[num]=ob;
    	  num++;
    	  return true;
    	}
    	return false;
    }
    
    
    public int searchIndex(BasicObject ob)
    	    {
    	    	for (int i=0;i<num;i++)
    	    		if (objects[i]==ob)
    	    		{
    	    			return i;
    	    		}
    	    	return -1;
    	    }
   
    public boolean remove(BasicObject ob)
    {
    	  int index=searchIndex(ob);
    		if (index!=-1)
    		{
    	    	objects[index]=objects[num-1];
    			objects[num-1]=null;
    			num--;
    			return true;
    		}
    	return false;
    }
    public BasicObject remove(int index)
    {
    	if(index>=0 && index<num)
    	{
    		BasicObject help=this.objects[index];
		   objects[index]=objects[num-1];
		   objects[num-1]=null;
		   num--;
		   return help;
    	}
    	return null;
    }
   
    public boolean removeWithOldOrder(BasicObject ob)
    {
    	    
    	int index=searchIndex(ob);
  		if (index!=-1)
  		{
    	  for (int i=index;i<num-1;i++)
    	  {
    		objects[i]=objects[i+1];
    	  }
    	  num--;
    	  return true;
  		}
  		return false;
    }
   public String toString()
    {
    	String help="";
    	for (int i=0;i<num;i++)
    		help+=objects[i]+"\n";
    	return help;
    }
  
}