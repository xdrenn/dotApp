package com.example.dot.di

import android.app.Application
import androidx.room.Room
import com.example.dot.data.TaskDatabase
import com.example.dot.data.TaskRepository
import com.example.dot.data.TaskRepositoryImpl
import com.example.dot.use_cases.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun provideDataBase(app: Application): TaskDatabase{
        return Room.databaseBuilder(
            app,
            TaskDatabase::class.java,
            TaskDatabase.DATABASE_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun provideRepository(db: TaskDatabase): TaskRepository{
        return TaskRepositoryImpl(db.taskDao)
    }

    @Provides
    @Singleton
    fun provideUseCases(repository: TaskRepository): TaskUseCases{
        return TaskUseCases(
            getTasks = GetTasksUseCase(repository),
            deleteTask = DeleteTaskUseCase(repository),
            addTask = AddTaskUseCase(repository),
            getTask = GetTaskUseCase(repository)
        )
    }
}