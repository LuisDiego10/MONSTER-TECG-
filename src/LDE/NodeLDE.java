package LDE;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

/**
 * Class Node
 * Declare the attributes for Node and constructor
 * @author Diego
 * @version 1.0
 * @since 6/11/2020
 */
@JsonIdentityInfo(generator= ObjectIdGenerators.UUIDGenerator.class, property="@id")
public class NodeLDE {
    public String fact;
    public String player;
    public String action;
    public NodeLDE nextNodeLDE;
    public NodeLDE prevNodeLDE;

    public String getFact(){
        return fact;
    }
}
