import React from "react";
import Post from "../Post/Post"
import {useState, useEffect} from "react";
function Home() {
    const [error,setError] = useState(null);
    const [isLoaded,SetIsLoaded] = useState(false);
    const [postList, setPostList] = useState([0]);
    
    useEffect(() => {
        fetch("/posts")
        .then(res => res.json())
        .then(
            (result) => {
                SetIsLoaded(true);
                setPostList(result);
            },
            (error) => {
                SetIsLoaded(true);
                setError(error);
            }
            
        ) 
    }, [])


    if(error){
        return <div>Error!</div>;
    }
    else if(!isLoaded){
        return <div>Loading</div>;
    }
    else {
        return(
            <div className="container">
                Home!!!
                    {postList.map(post => (
                        <Post title={post.title} text={post.text}></Post>
                    ))}
            </div>

            
        );
    }
  
    
}

export default Home;
