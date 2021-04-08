package krislet;
//
//	File:			Memory.java
//	Author:		Krzysztof Langner
//	Date:			1997/04/28
//

import java.util.ArrayList;
import java.util.Collection;

import jason.asSyntax.Atom;
import jason.asSyntax.Literal;
import jason.asSyntax.Structure;

class Memory 
{
    //---------------------------------------------------------------------------
    // This constructor:
    // - initializes all variables
    public Memory()
    {
    }

    private ArrayList<Literal> percepts = new ArrayList<Literal>();
    public Collection<Literal> getPercepts() {
    	return percepts;
    }

    public void updatePercepts() {
    	percepts.clear();
    	if (m_info != null) {
        	percepts.add(Literal.parseLiteral("time(" + m_info.getTime() + ")"));
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

