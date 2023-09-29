# SPRING_REST_API_streamingPlatform
Simple Rest API,created after watching the SPRING BOOT Udemy Course,with MVC.

A simple user named alice has been created,the password is 'test', and an admin with the same password.
Passwords are encrypted using bcrypt.

The users can be used with both API and MVC, the MVC has Admin and User views.The user is restricted to a simple view where he can only open youtube video by clicking on the 
video, while the Admin can do CRUD operations on Videos,Tags,Genres,Countries,Images and Users.
When you register a basic User is created and enabled.

In the API only admin can do crud operations while the user can retrieve a list of videos or a specific video by id.

The API and MVC projects are secured with configurations rathar than using annotation, like this: 

MVC:
.requestMatchers(HttpMethod.GET, "/videos/list").hasRole("USER")
.requestMatchers(HttpMethod.GET, "/videos/listAdmin").hasRole("ADMIN")
.requestMatchers(HttpMethod.GET, "/videos/showFormForAddVideo").hasRole("ADMIN")
.requestMatchers(HttpMethod.GET, "/videos/showSelectedVideo").hasRole("ADMIN")
.requestMatchers(HttpMethod.POST, "/videos/save").hasRole("ADMIN")
.requestMatchers(HttpMethod.GET, "/videos/showFormForUpdateVideo").hasRole("ADMIN")
.requestMatchers(HttpMethod.GET, "/videos/delete").hasRole("ADMIN")
                                
********************************************************************************************************************************
API:
  .requestMatchers(HttpMethod.GET, "/api/videos").hasRole("USER")
  .requestMatchers(HttpMethod.GET, "/api/videos/**").hasRole("USER")
  .requestMatchers(HttpMethod.POST, "/api/videos").hasRole("USER")
  .requestMatchers(HttpMethod.PUT, "/api/videos/**").hasRole("USER")
  .requestMatchers(HttpMethod.DELETE, "/api/videos/**").hasRole("USER")


Some links that helped creating this application: 
https://start.spring.io/
https://spring.io/guides#gettingStarted
https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#repositories.query-methods.details
https://docs.spring.io/spring-security/site/docs/4.2.3.RELEASE/reference/htmlsingle/
https://www.baeldung.com/spring-data-repositories

GUIDE:

*******************************************************DAL**********************************************************************

DATA ACCESS LAYER
Creating a Model is pretty straightforward, you create a Model class and add the @Entity annotation,
as for the ID,you need these 3 annotation 

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")

After you have created a class and added all needed annotation for validation and @Column for the properties you can create a Repository that extends the JPA Repository interface:
ALSO DONT FORGET THE @REPOSITORY ANNOTATION(you will get a bean error,simmilair like the error in asp when you dont add a scopedService)

    @Repository
    public interface VideoRepository extends JpaRepository<Video, Integer> {
    //jpa gives us all the basic CRUD operations that we need

    //https://docs.spring.io/spring-data/commons/docs/current/api/org/springframework/data/repository/CrudRepository.html
    //https://docs.spring.io/spring-data/jpa/docs/current/api/org/springframework/data/jpa/repository/JpaRepository.html
    //https://docs.spring.io/spring-data/commons/docs/current/api/org/springframework/data/repository/PagingAndSortingRepository.html

    //you can also write your own custom queries here!
     @Query(value = "select * from Video v where v.name like %:keyword%", nativeQuery = true)
    List<User> findByKeyword(@Param("keyword") String keyword);

    Lookup Defining Query Methods in Spring!
    }

Thats it for the DAL

**************************************************BLL*****************************************************************************

BUSSINESS LOGIC LAYER
Now you need to create an interface that will contain all CRUD operations you plan to use: 

    public interface VideoService {
    List<Video> findAll();
    Video findById(int id);
    Video save(Video video);
    void deleteById(int id);
    <------------------------------- CRUD operations for JPA
  
    Set<Tag> getTagsForVideo(int id);   <----- my custom metod
    }


After that we will create an Service implementation class: 

    @Service
    public class VideoServiceImpl implements VideoService {
    private final VideoRepository _videoRepository;
    private final GenreRepository _genreRepository;
    private final ImageRepository _imageRepository;

    @Autowired
    public VideoServiceImpl(VideoRepository videoRepository, GenreRepository genreRepository, ImageRepository imageRepository) {
        _videoRepository = videoRepository;
        _genreRepository = genreRepository;
        _imageRepository = imageRepository;
    }

    @Override
    public List<Video> findAll() {
        return _videoRepository.findAll();
    }

    @Override
    public Video findById(int id) {
        Optional<Video> videoOptional;
        videoOptional = _videoRepository.findById(id);

        if (videoOptional.isEmpty()){
            throw new CustomNotFoundException(("Video id not found - " + id));
        }

        return videoOptional.get();
    }

    @Override
    public Video save(Video video) {
        Optional<Genre> genreOptional;
        genreOptional = _genreRepository.findById(video.getGenreId());

        Optional<Image> imageOptional;
        imageOptional = _imageRepository.findById(video.getImageId());

        if (genreOptional.isEmpty()){
            throw new CustomNotFoundException(("Genre id not found - " + video.getGenreId()));
        }

        if (imageOptional.isEmpty()){
            throw new CustomNotFoundException(("Image id not found - " + video.getImageId()));
        }
        return _videoRepository.save(video);
    }

    @Override
    public Set<Tag> getTagsForVideo(int id) {
        Optional<Video> videoOpt = _videoRepository.findById(id);
        if (videoOpt.isPresent()) {
            return videoOpt.get().getTags();
        }
        return Collections.emptySet();
    }

    @Override
    public void deleteById(int id) {
        _videoRepository.deleteById(id);
    }
}

As you can see we are calling the videoRepo and using the methods that JPA provides for us!

Thats it! Now you can use all these CRUD operations in MVC and API.







