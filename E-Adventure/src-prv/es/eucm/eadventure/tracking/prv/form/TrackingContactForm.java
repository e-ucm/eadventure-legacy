/*******************************************************************************
 * <e-Adventure> (formerly <e-Game>) is a research project of the <e-UCM>
 *          research group.
 *   
 *    Copyright 2005-2012 <e-UCM> research group.
 *  
 *     <e-UCM> is a research group of the Department of Software Engineering
 *          and Artificial Intelligence at the Complutense University of Madrid
 *          (School of Computer Science).
 *  
 *          C Profesor Jose Garcia Santesmases sn,
 *          28040 Madrid (Madrid), Spain.
 *  
 *          For more info please visit:  <http://e-adventure.e-ucm.es> or
 *          <http://www.e-ucm.es>
 *  
 *  ****************************************************************************
 * This file is part of <e-Adventure>, version 1.4.
 * 
 *   You can access a list of all the contributors to <e-Adventure> at:
 *          http://e-adventure.e-ucm.es/contributors
 *  
 *  ****************************************************************************
 *       <e-Adventure> is free software: you can redistribute it and/or modify
 *      it under the terms of the GNU Lesser General Public License as published by
 *      the Free Software Foundation, either version 3 of the License, or
 *      (at your option) any later version.
 *  
 *      <e-Adventure> is distributed in the hope that it will be useful,
 *      but WITHOUT ANY WARRANTY; without even the implied warranty of
 *      MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *      GNU Lesser General Public License for more details.
 *  
 *      You should have received a copy of the GNU Lesser General Public License
 *      along with <e-Adventure>.  If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/

package es.eucm.eadventure.tracking.prv.form;

public class TrackingContactForm {

    public enum Gender{
        No_definido, Masculino, Femenino
    }
    
    public enum Occupation{
        No_definido, Alumno_de_primaria, Alumno_de_secundaria, Alumno_de_instituto, Alumno_universitario, Investigador, Profesor, Educador, Padre, Otro 
    }
    
    public enum TrackMe{
        Si, No
    }
    private String name;
    
    private String surname;
    
    private Integer age;
    
    private Gender gender;
    
    private Occupation occupation;
    
    private String email;
    
    private TrackMe trackingEnabled; 
    
    private String intendedUse;

    public TrackingContactForm(){
        name="";surname="";age=1;email="";gender=Gender.No_definido; occupation=Occupation.No_definido; trackingEnabled=TrackMe.Si; intendedUse="";
    }
    
    public String getName( ) {
    
        return name;
    }

    
    public void setName( String name ) {
    
        this.name = name;
    }

    
    public String getSurname( ) {
    
        return surname;
    }

    
    public void setSurname( String surname ) {
    
        this.surname = surname;
    }

    
    public Integer getAge( ) {
    
        return age;
    }

    
    public void setAge( Integer age ) {
    
        this.age = age;
    }

    
    public Gender getGender( ) {
    
        return gender;
    }

    
    public void setGender( Gender gender ) {
    
        this.gender = gender;
    }

    
    public Occupation getOccupation( ) {
    
        return occupation;
    }

    
    public void setOccupation( Occupation occupation ) {
    
        this.occupation = occupation;
    }

    
    public String getEmail( ) {
    
        return email;
    }

    
    public void setEmail( String email ) {
    
        this.email = email;
    }
    
    @Override
    public String toString(){
        return "nombre="+name+" apellidos="+surname+" age="+age+" gender="+gender+" occupation="+occupation+" email="+email+" trackme="+this.trackingEnabled+" intendeduse="+intendedUse;
    }

    
    public TrackMe getTrackingEnabled( ) {
    
        return trackingEnabled;
    }

    
    public void setTrackingEnabled( TrackMe trackingEnabled ) {
    
        this.trackingEnabled = trackingEnabled;
    }

    
    public String getIntendedUse( ) {
    
        return intendedUse;
    }

    
    public void setIntendedUse( String intendedUse ) {
    
        this.intendedUse = intendedUse;
    }
    
}
