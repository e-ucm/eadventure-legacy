/**
 * <e-Adventure> is an <e-UCM> research project. <e-UCM>, Department of Software
 * Engineering and Artificial Intelligence. Faculty of Informatics, Complutense
 * University of Madrid (Spain).
 * 
 * @author Del Blanco, A., Marchiori, E., Torrente, F.J. (alphabetical order) *
 * @author López Mañas, E., Pérez Padilla, F., Sollet, E., Torijano, B. (former
 *         developers by alphabetical order)
 * @author Moreno-Ger, P. & Fernández-Manjón, B. (directors)
 * @year 2009 Web-site: http://e-adventure.e-ucm.es
 */

/*
 * Copyright (C) 2004-2009 <e-UCM> research group
 * 
 * This file is part of <e-Adventure> project, an educational game & game-like
 * simulation authoring tool, available at http://e-adventure.e-ucm.es.
 * 
 * <e-Adventure> is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License as published by the Free
 * Software Foundation; either version 2 of the License, or (at your option) any
 * later version.
 * 
 * <e-Adventure> is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU General Public License along with
 * <e-Adventure>; if not, write to the Free Software Foundation, Inc., 59 Temple
 * Place, Suite 330, Boston, MA 02111-1307 USA
 * 
 */
package es.eucm.eadventure.common.data.chapter;

import java.util.ArrayList;
import java.util.List;

public class Trajectory implements Cloneable {

    List<Node> nodes;

    List<Side> sides;

    Node initial;

    public Trajectory( ) {

        nodes = new ArrayList<Node>( );
        sides = new ArrayList<Side>( );
        initial = null;
    }

    public Node addNode( String id, int x, int y, float scale ) {

        Node node = new Node( id, x, y, scale );
        if( nodes.contains( node ) ) {
            node = nodes.get( nodes.indexOf( node ) );
        }
        else {
            nodes.add( node );
        }
        if( nodes.size( ) == 1 ) {
            initial = nodes.get( 0 );
        }
        return node;
    }

    public Side addSide( String idStart, String idEnd, int length ) {

        if( idStart.equals( idEnd ) )
            return null;
        Side side = new Side( idStart, idEnd );
        Node a = getNodeForId( idStart );
        Node b = getNodeForId( idEnd );
        if( a != null && b != null ) {
           int x = a.getX( ) - b.getX( );
           int y = a.getY( ) - b.getY( );
           if (length == -1)
               side.setLenght( (float) Math.sqrt( x * x + y * y ) );
           else
               side.setLenght( length );
           side.setRealLength((float) Math.sqrt( x * x + y * y ));
        }
        
        if( sides.contains( side ) ) {
            return null;
        }
        else {
            sides.add( side );
        }
        return side;
    }

    public void removeNode( Node node ) {

        if( nodes.contains( node ) ) {
            node = nodes.get( nodes.indexOf( node ) );
            for( int i = 0; i < sides.size( ); ) {
                Side side = sides.get( i );
                if( side.getIDEnd( ).equals( node.getID( ) ) || side.getIDStart( ).equals( node.getID( ) ) )
                    sides.remove( i );
                else
                    i++;
            }
        }
        nodes.remove( node );
    }

    public void removeNode( int x, int y ) {

        Node node = new Node( "id", x, y, 1.0f );
        if( nodes.contains( node ) ) {
            node = nodes.get( nodes.indexOf( node ) );
            for( int i = 0; i < sides.size( ); ) {
                Side side = sides.get( i );
                if( side.getIDEnd( ).equals( node.getID( ) ) || side.getIDStart( ).equals( node.getID( ) ) )
                    sides.remove( i );
                else
                    i++;
            }
            nodes.remove( node );
        }

    }

    public List<Node> getNodes( ) {

        return nodes;
    }

    public List<Side> getSides( ) {

        return sides;
    }

    public class Node implements Cloneable {

        private String id;

        private int x;

        private int y;

        private float scale;

        public Node( String id, int x, int y, float scale ) {

            this.id = id;
            this.x = x;
            this.y = y;
            this.scale = scale;
        }

        public String getID( ) {

            return id;
        }

        public int getX( ) {

            return x;
        }

        public int getY( ) {

            return y;
        }

        public float getScale( ) {

            return scale;
        }

        public void setScale( float scale ) {

            this.scale = scale;
        }

        @Override
        public boolean equals( Object o ) {

            if( o == null )
                return false;
            if( o instanceof Node ) {
                Node node = (Node) o;
                if( node.id.equals( this.id ) )
                    return true;
                if( node.x == this.x && node.y == this.y )
                    return true;
            }
            return false;
        }

        public void setValues( int x, int y, float scale ) {

            this.x = x;
            this.y = y;
            this.scale = scale;
        }

        @Override
        public Object clone( ) throws CloneNotSupportedException {

            Node n = (Node) super.clone( );
            n.id = ( id != null ? new String( id ) : null );
            n.scale = scale;
            n.x = x;
            n.y = y;
            return n;
        }
    }

    public class Side implements Cloneable {

        private String idStart;

        private String idEnd;

        private float length = 1;
        
        private float realLength = 1;

        public Side( String idStart, String idEnd ) {

            this.idStart = idStart;
            this.idEnd = idEnd;
        }

        public void setRealLength( float realLength ) {
            this.realLength = realLength;
        }

        public String getIDStart( ) {

            return idStart;
        }

        public String getIDEnd( ) {

            return idEnd;
        }

        public void setLenght( float length ) {

            this.length = length;
        }

        @Override
        public boolean equals( Object o ) {

            if( o == null )
                return false;
            if( o instanceof Side ) {
                Side side = (Side) o;
                if( side.idEnd.equals( this.idEnd ) && side.idStart.equals( this.idStart ) )
                    return true;
            }
            return false;
        }

        public float getLength( ) {

            return length;
        }

        @Override
        public Object clone( ) throws CloneNotSupportedException {

            Side s = (Side) super.clone( );
            s.idEnd = ( idEnd != null ? new String( idEnd ) : null );
            s.idStart = ( idStart != null ? new String( idStart ) : null );
            s.length = length;
            return s;
        }

        public float getRealLength( ) {
            return realLength;
        }
    }

    public Node getNodeForId( String id ) {

        if( id == null )
            return null;
        for( Node node : nodes ) {
            if( id.equals( node.id ) )
                return node;
        }
        return null;
    }

    public void setInitial( String id ) {

        initial = getNodeForId( id );
    }

    public Node getInitial( ) {

        return initial;
    }

    public void deleteUnconnectedNodes( ) {
        List<Node> connected = new ArrayList<Node>( );
        if( initial == null ) {
            if( nodes.size( ) > 0 )
                initial = nodes.get( 0 );
        }
        connected.add( initial );
        int i = 0;
        while( i < connected.size( ) ) {
            Node temp = connected.get( i );
            i++;
            for( Side side : sides ) {
                if( side.getIDEnd( ).equals( temp.getID( ) ) ) {
                    Node node = this.getNodeForId( side.getIDStart( ) );
                    if( node != null && !connected.contains( node ) )
                        connected.add( node );
                }
                if( side.getIDStart( ).equals( temp.getID( ) ) ) {
                    Node node = this.getNodeForId( side.getIDEnd( ) );
                    if( node != null && !connected.contains( node ) )
                        connected.add( node );
                }
            }
        }
        i = 0;
        while( i < nodes.size( ) ) {
            if( !connected.contains( nodes.get( i ) ) ) {
                int j = 0;
                while( j < sides.size( ) ) {
                    if( sides.get( j ).getIDEnd( ).equals( nodes.get( i ).getID( ) ) )
                        sides.remove( j );
                    else if( sides.get( j ).getIDStart( ).equals( nodes.get( i ).getID( ) ) )
                        sides.remove( j );
                    else
                        j++;
                }
                nodes.remove( i );
            }
            else
                i++;
        }
    }

    @Override
    public Object clone( ) throws CloneNotSupportedException {

        Trajectory t = (Trajectory) super.clone( );
        t.initial = ( initial != null ? (Node) initial.clone( ) : null );
        if( nodes != null ) {
            t.nodes = new ArrayList<Node>( );
            for( Node n : nodes ) {
                if( n.equals( initial ) )
                    t.nodes.add( t.initial );
                else
                    t.nodes.add( (Node) n.clone( ) );
            }
        }
        if( sides != null ) {
            t.sides = new ArrayList<Side>( );
            for( Side s : sides )
                t.sides.add( (Side) s.clone( ) );
        }
        return t;
    }
}
