/**
 * Created by buha on 9/19/2015.
 */
interface Data
{
  public RealData getResult() throws InterruptedException;
}

class RealData
{

}

public class FeatureData implements Data
{
  private RealData realData;
  private boolean isReadonly;

  public FeatureData(boolean isReadonly)
  {
    this.isReadonly = isReadonly;
  }

  public RealData getResult() throws InterruptedException
  {
    this.wait();
    return this.realData;

  }

  public void SetData(RealData realData)
  {
    if (!isReadonly)
    {
      return;
    }
    ;
    this.realData = realData;
    isReadonly = true;
    notifyAll();
  }

  public static void main(String[] args) throws InterruptedException
  {
    FeatureData featureData = new FeatureData(false);
    try
    {
      RealData result = featureData.getResult();
//      featureData.SetData
    }
    catch (InterruptedException e)
    {
      e.printStackTrace();
    }
  }

}
