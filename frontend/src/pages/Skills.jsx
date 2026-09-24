import { useCallback, useEffect, useState } from "react";
import AppShell from "../components/AppShell"; import CourseCard from "../components/CourseCard";
import { getCourses, getProgress } from "../services/api"; import { LoadingState, ErrorState, EmptyState } from "../components/AsyncState";
export default function Skills({ user, page, onNavigate, onLogout, onOpen }) {
 const [courses,setCourses]=useState([]),[progress,setProgress]=useState({}),[error,setError]=useState(""),[loading,setLoading]=useState(true);
 const load=useCallback(async()=>{setLoading(true);setError("");try{const list=await getCourses();const results=await Promise.all(list.map(c=>getProgress(c.id,user.id).then(data=>[c.id,data])));setCourses(list);setProgress(Object.fromEntries(results));}catch(e){setError(e.message);}finally{setLoading(false);}},[user.id]);
 useEffect(()=>{const timer=setTimeout(load,0);return()=>clearTimeout(timer);},[load]);
 return <AppShell {...{user,page,onNavigate,onLogout}}><main className="content"><div className="page-heading"><span className="eyebrow">LEARNING LIBRARY</span><h1>Learn with a clear direction.</h1><p>Choose a practical path and work through short, focused lessons at your own pace.</p></div>{loading?<LoadingState text="Loading courses…"/>:error?<ErrorState message={error} onRetry={load}/>:!courses.length?<EmptyState title="No courses yet" text="Check back shortly for new learning paths."/>:<div className="course-grid">{courses.map(c=><CourseCard key={c.id} course={c} progress={progress} onOpen={onOpen}/>)}</div>}</main></AppShell>;
}
