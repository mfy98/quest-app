import React from "react";
import "./Post.scss";
function Post(props){
   const {title,text} = props;
   return(
        <div className="post-container">
            {title}
            {text}
        </div>
   )
}

export default Post;
