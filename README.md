# utopia-SS
Converted Utopia project into spring REST services

# Utopia Airlines Spring Project
This is a basic outline of the project. Upon design review outline is subject to change. 

### Database EER Diagram
!["Utopia Airlines Database Schema"](https://github.com/CtrlAltRock/utopia-SS/blob/dev/UtopiaAirlinesEERDiagram.JPG)

### Persona Services
Each ***Persona Microservice*** will be its own repository. Each microservice structure may vary, but each will expose a REST API for access. These services will have their own authentication and authorization.

---
***Admin Service***
Provides all functionality of the original Utopia Airlines CLI project description for admin services i.e. CRUD on database Entities.
Entity Access:
  * airport
  * airplane
  * airplane_type
  * route
  * flight
  * flight_bookings
  * booking
  * passenger
  * booking payment
  * booking agent
  * booking user
  * user
  * user_role

***Agent/Employee Service***
Provides minimal support for services and restricted access to entities.
Entity Access:
  * flight
  * booking
  * passenger
  
***User Service***
Provides the least support and little to no access to entities
Entity Access:
  * flight
