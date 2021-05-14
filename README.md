# —

# REST

### AccessRestService

Так происходит добавление/удаление пользователя из доступа на чтение/чтениезапись в файле  

[`http://localhost:8080/access?rw=1&grant=1&file=25&user=12`](http://localhost:8080/access?rw=1&grant=1&file=25&user=12)

```java
/**
     *
     * @param rw - readWrite or Read
     * @param grant - grant or decline
     * @param file - id of file
     * @param user - id of user who we are modifying access
     * @return
     */
    @PostMapping
    public List<UserDto> grantAccess(@RequestParam boolean rw,
                                     @RequestParam boolean grant,
                                     @RequestParam Long file,
                                     @RequestParam Long user){
        return accessService.modifyFileAccess(file, user, rw, grant);
    }
```

Опустим пару слоёв, и вот уже на слое  `FileAbstractDaoJpa` мы банально модифицируем set of R or RW access Users.  И  без разницы есть они уже в нём или нет их перед удалением, это сэт поэтому повторений не будет и ошибок на удаление не будет. 
На выход получаем собственно сэт который мы модифицировали со списком юзеров которые теперь имеют права на то, что мы модифицировали (R or RW) 

```java
@Override
    @Transactional
    public List<UserDto> grantRWAccess(Long fileId, Long userId) {
        FileAbstract fileAbstract = fileAbstractRepository.findById(fileId).orElseThrow(IdNotFoundException::new);
        User user = userRepository.findById(userId).orElseThrow(IdNotFoundException::new);
        em.merge(fileAbstract);
        em.merge(user);
        fileAbstract.getReadWritePermissionUsers().add(user);
        return userParser.fromList(new ArrayList<>(fileAbstract.getReadWritePermissionUsers()));
    }

    @Override
    @Transactional
    public List<UserDto> declineRWAccess(Long fileId, Long userId) {
        FileAbstract fileAbstract = fileAbstractRepository.findById(fileId).orElseThrow(IdNotFoundException::new);
        User user = userRepository.findById(userId).orElseThrow(IdNotFoundException::new);
        em.merge(fileAbstract);
        em.merge(user);
        fileAbstract.getReadWritePermissionUsers().remove(user);
        return userParser.fromList(new ArrayList<>(fileAbstract.getReadWritePermissionUsers()));
    }
}
```

### AccessService

модифицировать права на файл может только имеющий на чтение запись права пользователь

```java

public List<UserDto> modifyFileAccess(Long fileId, Long userId, boolean rw, boolean grant) {
        if (!chekRWAccess(fileId))
            throw new AccessDeniedException("You cant modify this file");

        if (rw)
            if (grant)
                return fileAbstractDaoJpa.grantRWAccess(fileId, userId);
            else
                return fileAbstractDaoJpa.declineRWAccess(fileId, userId);
        else
            if (grant)
                return fileAbstractDaoJpa.grantRAccess(fileId, userId);
            else
                return fileAbstractDaoJpa.declineRAccess(fileId, userId);
    }
```

Админ может всё, остальное приходится проверять

```java
public boolean chekRWAccess(Long id) {
        if (userService.getCurrentUser().getRole().equals("ADMIN"))
            return true;
        return fileAbstractDaoJpa.checkRWAccess(id);
    }

    public boolean chekRAccess(Long id) {
        if (userService.getCurrentUser().getRole().equals("ADMIN"))
            return true;
        return (fileAbstractDaoJpa.checkRAccess(id) || fileAbstractDaoJpa.checkRWAccess(id));
    }
```

### FileAbstractDaoJpa

тут достаточно понятно вроде описал алгоритм проверки прав доступа, происходит мудрёно, но даже не представляю как это можно сделать лучше, по крайней мере оно корректно отрабатывает.

```java
/**
     * Grant access to current user
     * If current file (or any parent file) creator current user
     * or
     * If current user has permissions to read current file (or any of parent file)
     *   (in other words he exists in any of ReadWritePermission lists)
     *
     */
    @Override
    @Transactional
    public boolean checkRWAccess(Long id) {
        FileAbstract fileAbstract = fileAbstractRepository.findById(id).orElseThrow(IdNotFoundException::new);
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        em.merge(fileAbstract);
        em.merge(user);
        while (fileAbstract != null){
            if (user.equals(fileAbstract.getUserCreatedBy()) ||
            fileAbstract.getReadWritePermissionUsers().contains(user))
                return true;
            else
                fileAbstract = fileAbstract.getParentCatalogue();
        }
        return false;
    }

    /**
     * Grant access to current user
     * If current file (or any parent file) creator is current user
     * or
     * If current user has permissions to read current file (or any of parent file)
     * or
     * If current file and avery parent file (up to root) don't have restrictions on read
     *   (in other words ReadPermission list empty for each)
     *   coz by task Read permission for everyone by default
     *
     */
    @Override
    @Transactional
    public boolean checkRAccess(Long id) {
        FileAbstract fileAbstract = fileAbstractRepository.findById(id).orElseThrow(IdNotFoundException::new);
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        em.merge(fileAbstract);
        em.merge(user);
        while (fileAbstract != null){
            if (user.equals(fileAbstract.getUserCreatedBy()) ||
                    fileAbstract.getReadPermissionUsers().contains(user))
                return true;
            else if (fileAbstract.getReadPermissionUsers().size() == 0)
                fileAbstract = fileAbstract.getParentCatalogue();
            else
                return false;
        }
        return true;
    }
```

Как можно заметить SecurityContextHolder у меня вызывается на Jpa слое, не знаю на сколько это уместно там, но именно там он мне только нужен, ведь далее наверх идут только ДТОшки

### DocumentService

то же самое распространяется и на CatalogueService
перед каждым соответствующем читающим или модифицирующим методом вызывается соответственно R or RW access checker, по айдишнику документа, айдишник пользователя который это вызывает получается как я и писал ранее на JPA слое 

```java
public DocumentDto getDocumentById(Long id){
        if (!accessService.chekRAccess(id))
            throw new AccessDeniedException("Access error");
        return documentDao.getDocumentById(id);
    }

    public List<ConcreteDocumentDto> getAllVersionsById(Long id){
        if (!accessService.chekRAccess(id))
            throw new AccessDeniedException("Access error");
        return documentDao.getAllVersions(id);
    }

    public DocumentDto saveNewDocument(DocumentDto documentDto, ConcreteDocumentDto concreteDocumentDto){
        if (!accessService.chekRWAccess(documentDto.getParentId()))
            throw new AccessDeniedException("Access error");
        documentDto.setUserCreatedById(userService.getCurrentUser().getId());
        concreteDocumentDto.setUserModifiedBy(userService.getCurrentUser().getId());
        return documentDao.addNewDocument(documentDto, concreteDocumentDto);
    }
```

---

---

---

# DocumentRestController

```java
@PostMapping
public DocumentDto addNewDocument(@RequestBody DocumentDto documentDto){
 return documentService.saveNewDocument(documentDto, documentDto.getTopVersionDocument());
}
```

Вот собственно такое нам надо на вход чтоб добавить новый документик

```java
{
  "parentId": 20, // Каталог в который добавляем
  "documentType": "fax", // тип файла (он возьмётся из БД если он там есть уже, 
												//либо создастся новый
  "priority": "HIGH", // приоритет (енум)
  "topVersionDocument": {   // ну и это вот как поле конкретного документа посылается
												
    "name": "newDoc_new",
    "description": "descr_new",
    "data": [   // а это вот вложенные файлы 
							// это объекты всё, они отдельно в БЖ хранятся 
						// (читай ниже)
      {
        "path": "paaa"
      },
      {
        "path": "ptthhhhh12"
      },
      {
        "path": "paa3"
      }
    ]
  }
}
```

Это мы на респонс получаем от пост запроса.

```json
{
"id": 32,
"parentId": 20,
"createdTime": "2021-05-14T10:00:27.093+00:00",
"userCreatedById": 2,
"name": "newDoc_new",
"typeOfFile": "DOCUMENT",
"documentType": "fax",
"priority": "HIGH",
	"topVersionDocument": {
	"id": 25,
	"name": "newDoc_new",
	"description": "descr_new",
	"version": 1,
	"modifiedTime": "2021-05-14T10:00:27.093+00:00",
	"userModifiedBy": 2,
	"parentDocumentId": 32,
		"data": [
					  {
					"id": 67,  // (вот они сохраняются, для них как то "условно" вычисляются 
								// имя, размер, берётся текущее дата время и сохраняется в бд
							// поля имя размер можно как бы и ручками передать изначально
						// просто предусмотрено опять таки условное их заполнение
						// надеюсь правильно понял твоё предложение с FilePath 
						// полноценной настоящей прогрузки файлов на сервер у меня не сделано 
					"name": "paaa",
					"size": 4,
					"path": "paaa",
					"parentConcreteDocumentId": null,
					"createdTime": "2021-05-14T10:00:27.149+00:00"
					},
					  {
					"id": 68,
					"name": "ptthhhhh12",
					"size": 10,
					"path": "ptthhhhh12",
					"parentConcreteDocumentId": null,
					"createdTime": "2021-05-14T10:00:27.149+00:00"
					},
					  {
					"id": 69,
					"name": "paa3",
					"size": 4,
					"path": "paa3",
					"parentConcreteDocumentId": null,
					"createdTime": "2021-05-14T10:00:27.149+00:00"
					}
		],
	}
}
```

### Modify

```java
@PostMapping("/modify")
public DocumentDto modifyDocument(@RequestBody ConcreteDocumentDto concreteDocumentDto){
     return documentService.modifyDocument(concreteDocumentDto);
}
```

тут на вход уже просто конкретный документ с прописанным родительским документом 

```json
{
  "name": "newDoc_mod3",
  "description": "mod_by_userid12",
  "parentDocumentId": 32,
  "data": [
    {
      "path": "paaa/pat"
    },
    {
      "path": "ptthhhhh12/12"
    },
    {
      "path": "paa3/12"
    }
  ]
}
```

тут соответственно так же чекаются права доступа
12й юзер имел права RW на 20й каталог, поэтому он может модифаить 32й док, поэтому тут же видно что док создан 2м юзером, а 3я версия создана 12м юзером 

```json
{
"id": 32,
"parentId": 20,
"createdTime": "2021-05-14T10:00:27.093+00:00",
"userCreatedById": 2,
"name": "newDoc_mod3",
"typeOfFile": "DOCUMENT",
"documentType": "fax",
"priority": "HIGH",
	"topVersionDocument": {
	"id": 28,
	"name": "newDoc_mod3",
	"description": "mod_by_userid12",
	"version": 3,
	"modifiedTime": "2021-05-14T10:15:12.290+00:00",
	"userModifiedBy": 12,
	"parentDocumentId": 32,
		"data": [
			  {
			"id": 76,
			"name": "paaa/pat",
			"size": 8,
			"path": "paaa/pat",
			"parentConcreteDocumentId": null,
			"createdTime": "2021-05-14T10:15:12.290+00:00"
			},
			  {
			"id": 77,
			"name": "ptthhhhh12/12",
			"size": 13,
			"path": "ptthhhhh12/12",
			"parentConcreteDocumentId": null,
			"createdTime": "2021-05-14T10:15:12.290+00:00"
			},
			  {
			"id": 78,
			"name": "paa3/12",
			"size": 7,
			"path": "paa3/12",
			"parentConcreteDocumentId": null,
			"createdTime": "2021-05-14T10:15:12.290+00:00"
			}
		],
	}
}
```