package krislet;
//
//	File:			Memory.java
//	Author:		Krzysztof Langner
//	Date:			1997/04/28
//

import java.util.ArrayList;

import jason.asSyntax.Literal;

class Memory 
{
    //---------------------------------------------------------------------------
    // This constructor:
    // - initializes all variables
    public Memory()
    {
    }

    private ArrayList<Literal> percepts = new ArrayList<Literal>();
    public ArrayList<Literal> getPercepts() {
    	return percepts;
    }

    public void updatePercepts() {
    	percepts.clear();
    	if (m_info != null) {
        	percepts.add(Literal.parseLiteral("time(" + m_info.getTime() + ")"));
        	for(int c = 0 ; c < m_info.m_objects.size() ; c ++) {
        		ObjectInfo object = (ObjectInfo)m_info.m_objects.elementAt(c);
        		// Jason freaks out if you try to add something to the knowledge base that has a space it in
        		// Note to jason that we found the object
        		percepts.add(Literal.parseLiteral("found(" + object.m_type + ")"));
				percepts.add(Literal.parseLiteral("dist(" + object.m_type + ',' + object.m_distance + ")"));
        		// note to jason that we are close to the object
        		if(object.m_distance < 1f) {
        			percepts.add(Literal.parseLiteral("beside(" + object.m_type + ")"));
        		}
        		if(object.m_distance < 15f) {
        			percepts.add(Literal.parseLiteral("close(" + object.m_type + ")"));
        		}
    	    }	
    	}
    }
    
    //---------------------------------------------------------------------------
    // This function puts see information into our memory
    public void store(VisualInfo info)
    {
	m_info = info;
    }

    //---------------------------------------------------------------------------
    // This function looks for specified object
    public ObjectInfo getObject(String name) 
    {
	if( m_info == null )
	    waitForNewInfo();

	for(int c = 0 ; c < m_info.m_objects.size() ; c ++)
	    {
		ObjectInfo object = (ObjectInfo)m_info.m_objects.elementAt(c);
		if(object.m_type.compareTo(name) == 0)
		    return object;
	    }												 

	return null;
    }


    //---------------------------------------------------------------------------
    // This function waits for new visual information
    public void waitForNewInfo() 
    {
	// first remove old info
	m_info = null;
	// now wait until we get new copy
	while(m_info == null)
	    {
		// We can get information faster then 75 miliseconds
		try
		    {
			Thread.sleep(SIMULATOR_STEP);
		    }
		catch(Exception e)
		    {
		    }
	    }
    }


    //===========================================================================
    // Private members
    volatile private VisualInfo	m_info;	// place where all information is stored
    final static int SIMULATOR_STEP = 100;
}

