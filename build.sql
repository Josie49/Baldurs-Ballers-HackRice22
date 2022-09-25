USE [HackRice12]
GO

/****** Object:  Table [dbo].[ASSIGNMENTS]    Script Date: 9/24/2022 8:39:59 PM ******/
IF  EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[ASSIGNMENTS]') AND type in (N'U'))
    DROP TABLE [dbo].[ASSIGNMENTS]
GO

/****** Object:  Table [dbo].[ASSIGNMENTS]    Script Date: 9/24/2022 8:37:50 PM ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE TABLE [dbo].[ASSIGNMENTS](
    [employeeID] [bigint] NOT NULL,
    [jobID] [bigint] NOT NULL,
    [startTime] [smallint] NOT NULL
) ON [PRIMARY]
GO

/****** Object:  Table [dbo].[EMPLOYEE]    Script Date: 9/24/2022 8:40:16 PM ******/
IF  EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[EMPLOYEE]') AND type in (N'U'))
    DROP TABLE [dbo].[EMPLOYEE]
GO

/****** Object:  Table [dbo].[EMPLOYEE]    Script Date: 9/24/2022 8:38:24 PM ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE TABLE [dbo].[EMPLOYEE](
     [employeeID] [bigint] NOT NULL,
     [phoneNumber] [varchar](50) NOT NULL,
     [startingAddress1] [varchar](max) NOT NULL,
     [startingAddress2] [varchar](max) NULL,
     [startingCity] [varchar](max) NOT NULL,
     [startingState] [nchar](2) NOT NULL,
     [startingZip] [varchar](50) NOT NULL,
     [shiftStart] [smallint] NOT NULL,
     [shiftEnd] [smallint] NOT NULL,
     CONSTRAINT [PK_EMPLOYEE] PRIMARY KEY CLUSTERED
         (
          [employeeID] ASC
             )WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO

/****** Object:  Table [dbo].[EMPLOYEE_SKILLS]    Script Date: 9/24/2022 8:39:24 PM ******/
IF  EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[EMPLOYEE_SKILLS]') AND type in (N'U'))
    DROP TABLE [dbo].[EMPLOYEE_SKILLS]
GO

/****** Object:  Table [dbo].[EMPLOYEE_SKILLS]    Script Date: 9/24/2022 8:39:24 PM ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE TABLE [dbo].[EMPLOYEE_SKILLS](
    [employeeID] [bigint] NOT NULL,
    [skillID] [smallint] NOT NULL
) ON [PRIMARY]
GO

/****** Object:  Table [dbo].[JOB]    Script Date: 9/24/2022 8:40:52 PM ******/
IF  EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[JOB]') AND type in (N'U'))
    DROP TABLE [dbo].[JOB]
GO

/****** Object:  Table [dbo].[JOB]    Script Date: 9/24/2022 8:40:52 PM ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE TABLE [dbo].[JOB](
    [jobID] [bigint] NOT NULL,
    [locationAddress1] [varchar](max) NOT NULL,
    [locationAddress2] [varchar](max) NULL,
    [locationCity] [varchar](max) NOT NULL,
    [locationState] [nchar](2) NOT NULL,
    [locationZip] [nchar](5) NOT NULL,
    [hours] [smallint] NOT NULL,
    [details] [varchar](max) NULL,
    [completion] [bit] NOT NULL,
    CONSTRAINT [PK_JOB] PRIMARY KEY CLUSTERED
        (
         [jobID] ASC
            )WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO

/****** Object:  Table [dbo].[JOB_SKILLS]    Script Date: 9/24/2022 8:41:16 PM ******/
IF  EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[JOB_SKILLS]') AND type in (N'U'))
    DROP TABLE [dbo].[JOB_SKILLS]
GO

/****** Object:  Table [dbo].[JOB_SKILLS]    Script Date: 9/24/2022 8:41:16 PM ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE TABLE [dbo].[JOB_SKILLS](
   [jobID] [bigint] NOT NULL,
   [skillID] [smallint] NOT NULL
) ON [PRIMARY]
GO

/****** Object:  Table [dbo].[SKILLS]    Script Date: 9/24/2022 8:41:38 PM ******/
IF  EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[SKILLS]') AND type in (N'U'))
    DROP TABLE [dbo].[SKILLS]
GO

/****** Object:  Table [dbo].[SKILLS]    Script Date: 9/24/2022 8:41:38 PM ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE TABLE [dbo].[SKILLS](
    [skillID] [smallint] NOT NULL,
    [skillName] [varchar](max) NOT NULL,
    CONSTRAINT [PK_SKILLS] PRIMARY KEY CLUSTERED
       (
        [skillID] ASC
           )WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO