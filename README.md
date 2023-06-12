# Auto Blog

Auto Blog is a RESTful API designed to automate the generation of blog articles using the power of OpenAI's ChatGPT.
This application serves as an intermediary between admin users and the ChatGPT API, providing a streamlined interface
for content creation, abstracting the complexities of prompt design, token management, and API interaction.

Admin users can request the generation of new articles by simply providing a title or a topic. The system then
communicates with the ChatGPT API to generate a full-length article based on the given input. The generated articles are
stored in a database and can be retrieved and displayed on a blog or any other content platform.

## Key Features

- **Automated Content Generation:** Leverages the capabilities of ChatGPT to generate unique, high-quality articles
  based on user-provided titles or topics.
- **Prompt Abstraction:** One of the key challenges when using AI models like ChatGPT is designing effective prompts.
  Auto Blog abstracts this complexity, allowing admins to simply provide a title or topic, and handling the prompt
  design and generation process behind the scenes.
- **Admin Interface:** Provides a secure interface for admin users to request the generation of new articles.
- **Content Management:** Auto Blog not only generates content but also manages it. The generated articles are stored in
  a database, allowing for easy retrieval, update, and display on a blog platform.
- **RESTful API:** Exposes endpoints for creating, retrieving, and managing generated articles, allowing for easy
  integration with various front-end platforms.

## Future Enhancements

- **User Accounts and Roles:** Implement user accounts with different roles (admin, editor, viewer) to allow for a
  collaborative content creation and management process.
- **Content Customization:** Provide more customization options for content generation, such as specifying the length,
  style, or format of the generated articles.

## Endpoints Map

### Implemented

- `GET /api/articles`: Retrieve a list of all articles.
- `GET /api/articles/titles`: Retrieve a list of all article titles.
- `GET /api/articles/article/{title}`: Retrieve a single article by its title.

### Planned

- `POST /api/auth/login`: Authenticate an admin user and return a JWT.
- `POST /api/articles/generate`: Generate a new article. Requires a JSON body with the title of the article to generate.

## Setup

Setting up the Auto Blog application involves running a Spring Boot application and setting up a PostgreSQL database.

1. Create a PostgreSQL Database, name it as you wish.
2. The following environmental variables should be added to the run configuration:
    - `SPRING_DATASOURCE_URL` typically looks like `jdbc:postgresql://localhost:5432/your_database_name`
    - `SPRING_DATASOURCE_USERNAME` the username which you created the database with.
    - `SPRING_DATASOURCE_PASSWORD` and its password.

- Or instead of environmental variables, place the values directly in `src/main/resources/application.properties` file.
