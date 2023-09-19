import React, {useState, useEffect} from "react";
import { ReactDOM } from "react";

function Post(){
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
            <ul>
            {postList.map(post => (
                <li>
                    {post.title} {post.text}
                </li>
            ))}
            </ul>
        );
    }
}

export default Post;
